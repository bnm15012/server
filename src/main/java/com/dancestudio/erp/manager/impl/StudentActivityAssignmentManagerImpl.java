package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entity.Payment;
import com.dancestudio.erp.entity.StudentActivityAssignment;
import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.enums.ExpenseCategory;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.manager.StudentActivityAssignmentManager;
import com.dancestudio.erp.repository.ExpenseRepository;
import com.dancestudio.erp.repository.MemberRepository;
import com.dancestudio.erp.repository.PaymentRepository;
import com.dancestudio.erp.repository.StudentActivityAssignmentRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Setter
public class StudentActivityAssignmentManagerImpl implements StudentActivityAssignmentManager {
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;

    @Autowired
    private PaymentManager paymentManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    public StudentActivityAssignmentManagerImpl(
            StudentActivityAssignmentRepository studentActivityAssignmentRepository) {
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
    }

    @Override
    public StudentActivityAssignmentEntry add(StudentActivityAssignmentEntry studentActivityAssignmentEntry)
            throws Exception {
        memberRepository.findById(studentActivityAssignmentEntry.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        StudentActivityAssignment studentStudentActivityAssignmentAssignment = convertToEntity(
                studentActivityAssignmentEntry, null);
        studentStudentActivityAssignmentAssignment = studentActivityAssignmentRepository
                .save(studentStudentActivityAssignmentAssignment);

        try {
            Long payeeId = studentStudentActivityAssignmentAssignment.getId();
            studentActivityAssignmentEntry.getPaymentEntry().setPayeeId(payeeId);

            paymentManager.add(studentActivityAssignmentEntry.getPaymentEntry());
        } catch (Exception ex) {
            throw new EntityNotFoundException("Failed to add payment details");
        }
        return convertToEntry(studentStudentActivityAssignmentAssignment);
    }

    @Override
    public StudentActivityAssignmentEntry update(Long studentActivityAssignmentId,
            StudentActivityAssignmentEntry studentActivityAssignmentEntry) throws Exception {
        StudentActivityAssignment existingStudentActivityAssignment = studentActivityAssignmentRepository
                .findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        StudentActivityAssignment updatedStudentActivityAssignment = convertToEntity(studentActivityAssignmentEntry,
                existingStudentActivityAssignment);
        updatedStudentActivityAssignment = studentActivityAssignmentRepository.save(updatedStudentActivityAssignment);

        return convertToEntry(studentActivityAssignmentRepository.save(updatedStudentActivityAssignment));
    }

    @Override
    public void delete(Long studentActivityAssignmentId) throws EntityNotFoundException {
        studentActivityAssignmentRepository.findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        PaymentEntry paymentEntry = paymentManager.getPaymentByPayeeIdAndPayeeType(studentActivityAssignmentId,
                PayeeType.STUDENT);
        try {
            paymentManager.delete(Long.valueOf(paymentEntry.getPaymentId()));
        } catch (Exception e) {
            throw new EntityNotFoundException("Failed to delete payment details");
        }

        studentActivityAssignmentRepository.deleteById(studentActivityAssignmentId);
    }

    @Override
    public StudentActivityAssignmentEntry getById(Long studentActivityAssignmentId) throws Exception {
        StudentActivityAssignment studentActivityAssignment = studentActivityAssignmentRepository
                .findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        return convertToEntry(studentActivityAssignment);
    }

    @Override
    public StudentActivityAssignmentEntry getStudentAssignmentsByStudentAndActivityId(Long studentId,
            String activityName) throws Exception {
        StudentActivityAssignment assignment = studentActivityAssignmentRepository
                .findByStudentIdAndActivityId(studentId, activityName);
        return convertToEntry(assignment);
    }

    @Override
    public List<StudentActivityAssignmentEntry> getStudentAssignmentsByStudentId(Long studentId) throws Exception {
        List<StudentActivityAssignment> enrollments = studentActivityAssignmentRepository.findByStudentId(studentId);
        List<StudentActivityAssignmentEntry> entries = new ArrayList<>();

        for (StudentActivityAssignment enrollment : enrollments) {
            entries.add(convertToEntry(enrollment));
        }

        return entries;
    }

    @Override
    public List<StudentActivityAssignmentEntry> getStudentByActivityIdAndStudioIdAndStatus(String activityName,
            Long studioId, String status) throws Exception {

        List<StudentActivityAssignment> entries = studentActivityAssignmentRepository
                .findStudentsWithActiveMemberships(activityName, studioId);
        List<StudentActivityAssignmentEntry> assignmentEntries = new ArrayList<>();

        for (StudentActivityAssignment entry : entries) {
            boolean isActive = entry.getMembershipEndDate().after(DateUtil.getCurrentDateUTC());
            if ((status.equalsIgnoreCase("ACTIVE") && isActive) || (status.equalsIgnoreCase("INACTIVE") && !isActive)) {
                assignmentEntries.add(convertToEntry(entry));
            }
        }
        return assignmentEntries;
    }

    @SneakyThrows
    @Override
    public List<MonthlyReportEntry> getAnalysisReport(Integer year, Long branchId) {
        List<MonthlyReportEntry> reportEntries = studentActivityAssignmentRepository
                .getAnalysisReport(Math.toIntExact(year), branchId);
        for (int month = 1; month <= 12; month++) {
            processMonthlyReport(reportEntries, month, year, branchId);
        }

        reportEntries.sort(Comparator.comparingInt(MonthlyReportEntry::getMonth));
        return reportEntries;
    }

    private void processMonthlyReport(List<MonthlyReportEntry> reportEntries, Integer month, Integer year,
            Long branchId) throws EntityNotFoundException {
        List<ExpenseEntry> expenseEntries = getExpenseEntriesForMonth(month, year, branchId);
        List<PaymentEntry> paymentEntries = getPaymentEntriesForMonth(month, year, branchId);
        double revenue = calculateRevenueForMonth(reportEntries, month);

        MonthlyReportEntry monthlyReportEntry = findOrCreateMonthlyReportEntry(reportEntries, month, revenue);
        monthlyReportEntry.setExpenseEntries(expenseEntries);
        monthlyReportEntry.setPaymentEntries(paymentEntries);
        monthlyReportEntry.setRevenue(revenue);
    }

    private MonthlyReportEntry findOrCreateMonthlyReportEntry(List<MonthlyReportEntry> reportEntries, int month,
            double revenue) {
        return reportEntries.stream().filter(entry -> entry.getMonth() == month)
                .findFirst()
                .orElseGet(() -> {
                    MonthlyReportEntry newEntry = new MonthlyReportEntry(month, revenue);
                    reportEntries.add(newEntry);
                    return newEntry;
                });
    }

    private List<ExpenseEntry> getExpenseEntriesForMonth(Integer month, Integer year, Long branchId)
            throws EntityNotFoundException {
        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(month, year, month, year);
        List<Object[]> expenses = expenseRepository.findCategoryWiseSumOfExpensesByDateRangeAndBranchId(
                monthRange.get("start"), monthRange.get("end"), branchId);
        List<ExpenseEntry> expenseEntries = new ArrayList<>();
        for (Object[] expense : expenses) {
            ExpenseEntry expenseEntry = new ExpenseEntry();
            expenseEntry.setExpenseCategory(ExpenseCategory.valueOf((String) expense[0]));
            expenseEntry.setAmount((Double) expense[1]);
            expenseEntries.add(expenseEntry);
        }

        return expenseEntries;
    }

    private List<PaymentEntry> getPaymentEntriesForMonth(Integer month, Integer year, Long studioId) {
        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(month, year, month, year);
        List<Object[]> payments = paymentRepository.findCategoryWiseSumOfPaymentsByDateRange(studioId,
                monthRange.get("start"), monthRange.get("end"));
        List<PaymentEntry> paymentEntries = new ArrayList<>();

        for (Object[] payment : payments) {
            PaymentEntry paymentEntry = new PaymentEntry();
            paymentEntry.setPayeeType(PayeeType.valueOf((String) payment[0]));
            paymentEntry.setAmount((Double) payment[1]);
            paymentEntries.add(paymentEntry);
        }

        return paymentEntries;
    }

    private double calculateRevenueForMonth(List<MonthlyReportEntry> reportEntries, int month) {
        return reportEntries.stream().filter(entry -> entry.getMonth() == month)
                .mapToDouble(MonthlyReportEntry::getRevenue)
                .sum();
    }

    private StudentActivityAssignmentEntry convertToEntry(StudentActivityAssignment studentActivityAssignment)
            throws Exception {

        if (Objects.isNull(studentActivityAssignment)) {
            return null;
        }

        StudentActivityAssignmentEntry studentActivityAssignmentEntry = new StudentActivityAssignmentEntry();
        studentActivityAssignmentEntry.setAssignmentId(studentActivityAssignment.getId());
        studentActivityAssignmentEntry.setStudentId(studentActivityAssignment.getStudent().getId());
        studentActivityAssignmentEntry.setRegistrationDate(studentActivityAssignment.getRegistrationDate());
        studentActivityAssignmentEntry.setBatchName(studentActivityAssignment.getBatchName());
        studentActivityAssignmentEntry.setBatchTime(studentActivityAssignment.getBatchTime());
        studentActivityAssignmentEntry.setMembershipStartDate(studentActivityAssignment.getMembershipStartDate());
        studentActivityAssignmentEntry.setMembershipEndDate(studentActivityAssignment.getMembershipEndDate());
        studentActivityAssignmentEntry.setDaysPerWeek(studentActivityAssignment.getDaysPerWeek());
        studentActivityAssignmentEntry.setMembershipStatus(
                studentActivityAssignment.getMembershipEndDate().after(DateUtil.getCurrentDateUTC())
                        ? MembershipStatus.ACTIVE
                        : MembershipStatus.INACTIVE);
        studentActivityAssignmentEntry
                .setMembershipType((studentActivityAssignment.getMembershipType()));
        studentActivityAssignmentEntry.setActivityAmount(studentActivityAssignment.getActivityAmount());

        if (Objects.nonNull(studentActivityAssignment.getActivityName())) {
            studentActivityAssignmentEntry.setActivityName(studentActivityAssignment.getActivityName());
        }

        Payment payment = paymentRepository.findByPayeeId(studentActivityAssignment.getId());
        studentActivityAssignmentEntry.setPaymentEntry(ConvertToEntryUtil.convertToEntry(payment));

        return studentActivityAssignmentEntry;
    }

    private StudentActivityAssignment convertToEntity(StudentActivityAssignmentEntry studentActivityAssignmentEntry,
            StudentActivityAssignment existingStudentActivityAssignment) throws Exception {
        StudentActivityAssignment studentActivityAssignment = (existingStudentActivityAssignment != null)
                ? existingStudentActivityAssignment
                : new StudentActivityAssignment();

        if (Objects.nonNull(studentActivityAssignmentEntry.getAssignmentId())) {
            studentActivityAssignment.setId(studentActivityAssignmentEntry.getAssignmentId());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getRegistrationDate())) {
            studentActivityAssignment.setRegistrationDate(studentActivityAssignmentEntry.getRegistrationDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getBatchName())) {
            studentActivityAssignment.setBatchName(studentActivityAssignmentEntry.getBatchName());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getBatchTime())) {
            studentActivityAssignment.setBatchTime(studentActivityAssignmentEntry.getBatchTime());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipStartDate())) {
            studentActivityAssignment.setMembershipStartDate(studentActivityAssignmentEntry.getMembershipStartDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipEndDate())) {
            studentActivityAssignment.setMembershipEndDate(studentActivityAssignmentEntry.getMembershipEndDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipType())) {
            studentActivityAssignment.setMembershipType(studentActivityAssignmentEntry.getMembershipType());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getActivityAmount())) {
            studentActivityAssignment.setActivityAmount(studentActivityAssignmentEntry.getActivityAmount());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getDaysPerWeek())) {
            studentActivityAssignment.setDaysPerWeek(studentActivityAssignmentEntry.getDaysPerWeek());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getStudentId())) {
            Member student = memberRepository.findById(studentActivityAssignmentEntry.getStudentId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            studentActivityAssignment.setStudent(student);
        }

        if (Objects.nonNull(studentActivityAssignmentEntry.getActivityName())) {
            studentActivityAssignment.setActivityName(studentActivityAssignmentEntry.getActivityName());
        }

        return studentActivityAssignment;
    }
}
