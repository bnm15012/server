package com.dancestudio.erp.util;

import com.dancestudio.erp.entity.*;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.*;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.impl.BranchManagerImpl;
import com.dancestudio.erp.manager.impl.StudentActivityAssignmentManagerImpl;
import com.dancestudio.erp.manager.impl.StudioManagerImpl;
import com.dancestudio.erp.manager.impl.SubscriptionManagerImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class ConvertToEntryUtil {

    private static ApplicationContext applicationContext;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static UserEntry convertToEntry(User user) throws Exception {

        UserEntry userEntry = new UserEntry();
        userEntry.setUserId(user.getId());
        userEntry.setUserName(user.getName());
        userEntry.setEmail(user.getEmail());
        userEntry.setPhone(user.getPhone());
        userEntry.setImageUrl(user.getProfileImage());
        userEntry.setEnabled(user.isEnabled());
        userEntry.setRole(UserType.valueOf(user.getRole()));

        if (Objects.nonNull(user.getStudio().getId())) {
            StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
            StudioEntry studioEntry = studioManagerImpl.getById(user.getStudio().getId());
            userEntry.setStudioEntry(studioEntry);

            SubscriptionManagerImpl subscriptionManagerImpl = applicationContext.getBean(SubscriptionManagerImpl.class);
            SubscriptionEntry subscriptionEntry = subscriptionManagerImpl
                    .getSubscriptionPlanByStudioId(user.getStudio().getId());

            userEntry.setSubscriptionEntry(subscriptionEntry);
        }

        BranchManagerImpl branchManagerImpl = applicationContext.getBean(BranchManagerImpl.class);
        if (isAdmin(user)) {
            setBranchListForAdmin(user, userEntry, branchManagerImpl);
        } else {
            setBranchListForNonAdmin(user, userEntry, branchManagerImpl);
        }

        return userEntry;
    }

    private static boolean isAdmin(User user) {
        return UserType.ADMIN.name().equals(user.getRole());
    }

    private static void setBranchListForAdmin(User user, UserEntry userEntry, BranchManagerImpl branchManagerImpl)
            throws Exception {
        List<BranchEntry> branchEntries = branchManagerImpl.findByStudioId(user.getStudio().getId());
        userEntry.getStudioEntry().setBranchList(branchEntries);
    }

    private static void setBranchListForNonAdmin(User user, UserEntry userEntry, BranchManagerImpl branchManagerImpl)
            throws Exception {
        if (Objects.nonNull(user.getBranch().getId())) {
            BranchEntry branchEntry = branchManagerImpl.getById(user.getBranch().getId());
            if (branchEntry.getIsActive()) {
                userEntry.getStudioEntry().setBranchList(Collections.singletonList(branchEntry));
            } else {
                userEntry.getStudioEntry().setBranchList(null);
            }
        }
    }

    public static User convertToEntity(UserEntry userEntry, User existingUser) throws Exception {
        User user = (existingUser != null) ? existingUser : new User();
        user.setId(null);

        if (Objects.nonNull(userEntry.getUserId())) {
            user.setId(userEntry.getUserId());
        }
        if (Objects.nonNull(userEntry.getUserName())) {
            user.setName(userEntry.getUserName());
        }
        if (Objects.nonNull(userEntry.getPassword())) {
            user.setPassword(userEntry.getPassword());
        }
        if (Objects.nonNull(userEntry.getRole())) {
            user.setRole(String.valueOf(userEntry.getRole()));
        }
        if (Objects.nonNull(userEntry.getPhone())) {
            user.setPhone(userEntry.getPhone());
        }
        if (Objects.nonNull(userEntry.getImageUrl())) {
            user.setProfileImage(userEntry.getImageUrl());
        }
        if (Objects.nonNull(userEntry.getEmail())) {
            user.setEmail(userEntry.getEmail());
        }
        if (Objects.nonNull(userEntry.getEnabled())) {
            user.setEnabled(userEntry.getEnabled());
        }

        if (Objects.nonNull(userEntry.getStudioEntry()) && Objects.nonNull(userEntry.getStudioEntry().getStudioId())) {
            Long studioId = userEntry.getStudioEntry().getStudioId();

            StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
            StudioEntry studioEntry = studioManagerImpl.getById(studioId);
            user.setStudio(convertToEntity(studioEntry, null));
        }

        if (Objects.nonNull(userEntry.getStudioEntry())
                && !CollectionUtils.isEmpty(userEntry.getStudioEntry().getBranchList())) {
            Long branchId = userEntry.getStudioEntry().getBranchList().get(0).getBranchId();

            BranchManagerImpl branchManagerImpl = applicationContext.getBean(BranchManagerImpl.class);
            BranchEntry branchEntry = branchManagerImpl.getById(branchId);
            user.setBranch(convertToEntity(branchEntry, null));
        }

        return user;
    }

    public static StudioEntry convertToEntry(Studio studio) throws Exception {

        StudioEntry studioEntry = new StudioEntry();
        studioEntry.setStudioId(studio.getId());
        studioEntry.setStudioName(studio.getName());
        studioEntry.setLocation(studio.getLocation());
        studioEntry.setLogo(studio.getLogo());
        studioEntry.setEmail(studio.getEmail());
        studioEntry.setPasscode(studio.getPasscode());
        studioEntry.setContactDetails(studio.getContactDetails());

        if (studio.getConfiguration() != null) {
            try {
                List<StudioConfigurationEntry> configrationEntries = objectMapper.readValue(studio.getConfiguration(),
                        new TypeReference<>() {
                        });
                StudioConfigurationRequest request = new StudioConfigurationRequest();
                request.setConfigrationEntryList(configrationEntries);
                studioEntry.setConfiguration(request);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing configuration settings JSON ", e);
            }
        }

        try {
            BranchManagerImpl branchManager = applicationContext.getBean(BranchManagerImpl.class);
            List<BranchEntry> branchEntries = branchManager.findByStudioId(studio.getId());
            studioEntry.setBranchList(branchEntries);
        } catch (Exception ex) {
            studioEntry.setBranchList(null);
        }

        return studioEntry;
    }

    public static Studio convertToEntity(StudioEntry studioEntry, Studio existingStudio) {
        Studio studio = (existingStudio != null) ? existingStudio : new Studio();

        if (Objects.nonNull(studioEntry.getStudioId())) {
            studio.setId(studioEntry.getStudioId());
        }
        if (Objects.nonNull(studioEntry.getStudioName())) {
            studio.setName(studioEntry.getStudioName());
        }
        if (Objects.nonNull(studioEntry.getLogo())) {
            studio.setLogo(studioEntry.getLogo());
        }
        if (Objects.nonNull(studioEntry.getEmail())) {
            studio.setEmail(studioEntry.getEmail());
        }
        if (Objects.nonNull(studioEntry.getPasscode())) {
            studio.setPasscode(studioEntry.getPasscode());
        }
        if (Objects.nonNull(studioEntry.getLocation())) {
            studio.setLocation(studioEntry.getLocation());
        }
        if (Objects.nonNull(studioEntry.getContactDetails())) {
            studio.setContactDetails(studioEntry.getContactDetails());
        }
        if (Objects.nonNull(studioEntry.getConfiguration())) {
            List<StudioConfigurationEntry> configrationEntries = studioEntry.getConfiguration()
                    .getConfigrationEntryList();
            try {
                String configurationJson = objectMapper.writeValueAsString(configrationEntries);
                studio.setConfiguration(configurationJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting configuration settings to JSON", e);
            }
        }

        return studio;
    }

    public static Client convertToEntity(ClientEntry clientEntry, Client existingClient) {
        Client client = (existingClient != null) ? existingClient : new Client();

        if (Objects.nonNull(clientEntry.getClientId())) {
            client.setId(clientEntry.getClientId());
        }
        if (Objects.nonNull(clientEntry.getGroupName())) {
            client.setGroupName(clientEntry.getGroupName());
        }
        if (Objects.nonNull(clientEntry.getPocName())) {
            client.setPocName(clientEntry.getPocName());
        }
        if (Objects.nonNull(clientEntry.getPocPhone())) {
            client.setPocPhone(clientEntry.getPocPhone());
        }
        if (Objects.nonNull(clientEntry.getPocEmail())) {
            client.setPocEmail(clientEntry.getPocEmail());
        }
        if (Objects.nonNull(clientEntry.getClientType())) {
            client.setClientType(clientEntry.getClientType().name());
        }
        if (Objects.nonNull(clientEntry.getNotes())) {
            client.setNotes(clientEntry.getNotes());
        }

        return client;
    }

    public static BankAccountEntry convertToEntry(BankAccount bankAccount) {

        if (Objects.isNull(bankAccount)) {
            return null;
        }

        BankAccountEntry bankAccountEntry = new BankAccountEntry();
        bankAccountEntry.setBankAccountId(bankAccount.getId());
        bankAccountEntry.setBankName(bankAccount.getBankName());
        bankAccountEntry.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountEntry.setBranchName(bankAccount.getBranchName());
        bankAccountEntry.setIfscCode(bankAccount.getIfscCode());
        bankAccountEntry.setUpiId(bankAccount.getUpiId());
        bankAccountEntry.setInstructorId(bankAccount.getInstructorId());
        return bankAccountEntry;
    }

    public static BankAccount convertToEntity(BankAccountEntry bankAccountEntry, BankAccount existingBankAccount)
            throws EntityNotFoundException {
        BankAccount bankAccount = (existingBankAccount != null) ? existingBankAccount : new BankAccount();

        if (Objects.nonNull(bankAccountEntry.getBankAccountId())) {
            bankAccount.setId(bankAccountEntry.getBankAccountId());
        }
        if (Objects.nonNull(bankAccountEntry.getAccountNumber())) {
            bankAccount.setAccountNumber(bankAccountEntry.getAccountNumber());
        }
        if (Objects.nonNull(bankAccountEntry.getBankName())) {
            bankAccount.setBankName(bankAccountEntry.getBankName());
        }
        if (Objects.nonNull(bankAccountEntry.getBranchName())) {
            bankAccount.setBranchName(bankAccountEntry.getBranchName());
        }
        if (Objects.nonNull(bankAccountEntry.getIfscCode())) {
            bankAccount.setIfscCode(bankAccountEntry.getIfscCode());
        }
        if (Objects.nonNull(bankAccountEntry.getUpiId())) {
            bankAccount.setUpiId(bankAccountEntry.getUpiId());
        }
        if (Objects.nonNull(bankAccountEntry.getInstructorId())) {
            bankAccount.setInstructorId(bankAccountEntry.getInstructorId());
        }
        return bankAccount;
    }

    public static ExpenseEntry convertToEntry(Expense expense) throws Exception {

        ExpenseEntry expenseEntry = new ExpenseEntry();
        expenseEntry.setExpenseId(expense.getId());
        expenseEntry.setAmount(expense.getAmount());
        expenseEntry.setDescription(expense.getDescription());

        StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
        StudioEntry studioEntry = studioManagerImpl.getById(expense.getBranch().getId());

        expenseEntry.setBranchId(studioEntry.getStudioId());
        expenseEntry.setExpenseDate(expense.getExpenseDate());
        expenseEntry.setExpenseCategory(ExpenseCategory.valueOf(expense.getExpenseCategory()));

        return expenseEntry;
    }

    public static PaymentEntry convertToEntry(Payment payment) {

        PaymentEntry paymentEntry = new PaymentEntry();
        paymentEntry.setPaymentId(String.valueOf(payment.getId()));
        paymentEntry.setPayeeId(payment.getPayeeId());
        paymentEntry.setPayeeType(PayeeType.valueOf(payment.getPayeeType()));
        paymentEntry.setAmount(payment.getAmount());
        paymentEntry.setActualAmount(payment.getActualAmount());
        paymentEntry.setPaymentDate(payment.getPaymentDate());
        paymentEntry.setStatus(PaymentStatus.valueOf(payment.getStatus()));
        paymentEntry.setPaymentType(PaymentType.valueOf(payment.getPaymentType()));
        paymentEntry.setBranchId(payment.getBranch().getId());
        return paymentEntry;
    }

    public static StudentEntry convertToEntry(Member student) {

        StudentEntry studentEntry = new StudentEntry();
        studentEntry.setStudentId(student.getId());
        studentEntry.setName(student.getName());
        studentEntry.setPhone(student.getPhone());
        studentEntry.setDob(student.getDob());
        studentEntry.setEmail(student.getEmail());
        studentEntry.setImageUrl(student.getProfileImage());
        studentEntry.setAddress(student.getAddress());
        studentEntry.setEmergencyContactNumber(student.getEmergencyContactNumber());

        try {

            StudentActivityAssignmentManagerImpl studentActivityAssignmentManagerImpl = applicationContext
                    .getBean(StudentActivityAssignmentManagerImpl.class);
            List<StudentActivityAssignmentEntry> entries = studentActivityAssignmentManagerImpl
                    .getStudentAssignmentsByStudentId(student.getId());
            boolean isActive = false;
            for (StudentActivityAssignmentEntry entry : entries) {
                if (entry.getMembershipEndDate().after(DateUtil.getCurrentDateUTC())) {
                    isActive = true;
                    break;
                }
            }
            studentEntry.setMembershipStatus(isActive ? MembershipStatus.ACTIVE : MembershipStatus.INACTIVE);
            studentEntry.setEnrolledActivities(entries);
        } catch (Exception ex) {
            studentEntry.setEnrolledActivities(null);
        }
        return studentEntry;
    }

    public static Branch convertToEntity(BranchEntry branchEntry, Branch existingBranch) throws Exception {
        Branch branch = (existingBranch != null) ? existingBranch : new Branch();

        if (branchEntry.getBranchId() != null) {
            branch.setId(branchEntry.getBranchId());
        }
        if (branchEntry.getName() != null) {
            branch.setName(branchEntry.getName());
        }
        if (branchEntry.getAddress() != null) {
            branch.setAddress(branchEntry.getAddress());
        }
        if (branchEntry.getCity() != null) {
            branch.setCity(branchEntry.getCity());
        }
        if (branchEntry.getState() != null) {
            branch.setState(branchEntry.getState());
        }
        if (branchEntry.getPincode() != null) {
            branch.setPincode(branchEntry.getPincode());
        }
        if (branchEntry.getPhone() != null) {
            branch.setPhone(branchEntry.getPhone());
        }
        if (branchEntry.getIsActive() != null) {
            branch.setIsActive(branchEntry.getIsActive());
        }
        if (Objects.nonNull(branchEntry.getStudioId())) {
            StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
            StudioEntry studioEntry = studioManagerImpl.getById(branchEntry.getStudioId());
            branch.setStudio(ConvertToEntryUtil.convertToEntity(studioEntry, null));
        }

        return branch;
    }

}
