package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.entity.Message;
import com.dancestudio.erp.entity.MessageRecipient;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.NotificationType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.message_queue.services.EmailService;
import com.dancestudio.erp.modules.template.template.TemplateEntry;
import com.dancestudio.erp.modules.template.template.TemplateManager;
import com.dancestudio.erp.repository.MessageRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;
import com.dancestudio.erp.util.WhatsappUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.dancestudio.erp.constants.TemplateName.ADD_NEW_STUDENT_EMAIL;

@Service
@Slf4j
@Setter
public class StudentManagerImpl implements StudentManager {

    private final EmailService emailService;

    private final MemberRepository memberRepository;
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;
    private final MessageRepository messageRepository;

    @Autowired private NotificationManager notificationManager;
    @Autowired private StudioManager studioManager;
    @Autowired private BranchManager branchManager;
    @Autowired private TemplateManager templateManager;
    @Autowired private StudentActivityAssignmentManager studentActivityAssignmentManager;
    @Autowired private WhatsappUtil whatsappUtil;

    @Autowired
    public StudentManagerImpl(MemberRepository memberRepository, StudentActivityAssignmentRepository studentActivityAssignmentRepository, EmailService emailService, MessageRepository messageRepository) {
        this.memberRepository = memberRepository;
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
        this.emailService = emailService;
        this.messageRepository = messageRepository;
    }

    @Override
    public StudentEntry add(StudentEntry studentEntry) throws Exception {
        if (memberRepository.findByNameAndMemberTypeAndEmail(studentEntry.getName(), MemberType.STUDENT.name(), studentEntry.getEmail()).isPresent()) {
            throw new Exception("Student already exists");
        }

        Member member = convertToEntity(studentEntry, null);
        member = memberRepository.save(member);

        TemplateEntry templateEntry = templateManager.getTemplateDetails(ADD_NEW_STUDENT_EMAIL);

        BranchEntry branchEntry = branchManager.getById(member.getBranch().getId());
        StudioEntry studioEntry = studioManager.getById(branchEntry.getStudioId());

        String updatedBody = formatEmailBody(studioEntry, templateEntry, member);
        if(Objects.nonNull(studioEntry.getPasscode()) && Objects.nonNull(studioEntry.getEmail())) {

            Message message =messageRepository.save(new Message(member.getBranch(), false,
                    templateEntry.getSubject(), templateEntry.getTemplateBody(),
                    NotificationType.EMAIL.name()));
            MessageRecipient recepient = new MessageRecipient(message, member.getName(), member.getEmail(),
                    MessageStatus.PENDING, null);
            emailService.sendHighPriorityEmail(member.getEmail(), templateEntry.getSubject(), updatedBody, branchEntry.getStudioId(), null, null, recepient);
        }

        boolean msgSent = whatsappUtil.sendMessage(member.getPhone(), updatedBody, member.getBranch().getId(), null, null, null);
        if(!msgSent) {
            log.error("Failed to send WhatsApp message to student: {}", member.getName());
        }

        return convertToEntry(member);
    }

    @Override
    public StudentEntry update(Long studentId, StudentEntry studentEntry) throws Exception {
        Member existingStudent = memberRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Member updatedStudent = convertToEntity(studentEntry, existingStudent);
        updatedStudent = memberRepository.save(updatedStudent);

        return convertToEntry(updatedStudent);
    }

    @Override
    public void delete(Long studentId) throws EntityNotFoundException {
        memberRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        memberRepository.deleteById(studentId);
    }

    @Override
    public StudentEntry getById(Long studentId) throws EntityNotFoundException {
        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return convertToEntry(student);
    }

    @Override
    public List<StudentEntry> getAllStudentsByStudio(Long branchId, String activityName, MembershipStatus membershipStatus, int page, int size, String searchTerm) {
        if (size == -1) {
            List<Member> entries = memberRepository.findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, activityName, null, searchTerm);
            return entries.stream()
                    .map(this::convertToEntry)
                    .collect(Collectors.toList());
        } else {
            Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
            Page<Member> studentPage = memberRepository.findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, activityName, membershipStatus != null ? membershipStatus.name() : null, pageable, searchTerm);
            List<StudentEntry> entries =  studentPage.getContent().stream()
                    .map(this::convertToEntry)
                    .toList();

            if (membershipStatus == null) {
                return entries;
            }

            return entries.stream()
                    .filter(student -> student.getMembershipStatus() == membershipStatus)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<StudentCommunicationEntry> getAllStudentsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size, int birthday) {
        List<Member> entries;
        if (size == -1) {
            entries = memberRepository.findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, null, membershipStatus.name(), null);
        } else {
            Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
            Page<Member> studentPage = memberRepository.findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, null, membershipStatus.name(), pageable, null);
            entries = studentPage.getContent().stream().toList();
        }

        if (birthday == 1) {
            entries = entries.stream()
                    .filter(student -> DateUtil.isTodaysDate(student.getDob()))
                    .collect(Collectors.toList());
        }

        List<StudentCommunicationEntry> studentEntries = new ArrayList<>();
        for (Member student : entries) {
            StudentCommunicationEntry entry = new StudentCommunicationEntry();
            entry.setStudentId(student.getId());
            entry.setName(student.getName());
            studentEntries.add(entry);
        }
        return studentEntries;
    }

    @Override
    public List<StudentEntry> findByMembershipEndDate(Date reminderDate) {
        List<Long> studentIds = studentActivityAssignmentRepository.findStudentIdsWithMembershipEndingOnDate(reminderDate);

        List<StudentEntry> studentEntries = new ArrayList<>();
        for (Long studentId : studentIds) {
            try {
                StudentEntry studentEntry = getById(studentId);
                studentEntries.add(studentEntry);
            } catch (EntityNotFoundException ex) {
                log.error("Entity not found : {}", ex.getMessage());
            }
        }
        return studentEntries;
    }

    @Override
    public boolean sendSubscriptionRenewalReminder(Long studentId, String activityName) throws Exception {
        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        StudentActivityAssignmentEntry entry = studentActivityAssignmentManager.getStudentAssignmentsByStudentAndActivityId(studentId, activityName);
        if (Objects.isNull(entry)) {
            throw new EntityNotFoundException("No active subscription found");
        }

        notificationManager.sendSubscriptionRenewalEmail(student, entry, student.getBranch().getStudio().getName());
        return true;
    }

    private String formatEmailBody(StudioEntry studioEntry, TemplateEntry templateEntry, Member student) {
        return templateEntry.getTemplateBody()
                .replace("{student_name}", student.getName())
                .replace("{studio_name}", studioEntry.getStudioName());
    }

    private StudentEntry convertToEntry(Member student) {

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
            List<StudentActivityAssignmentEntry> entries = studentActivityAssignmentManager.getStudentAssignmentsByStudentId(student.getId());
            boolean isActive = false;
            for (StudentActivityAssignmentEntry entry : entries) {
                if (entry.getMembershipEndDate().after(DateUtil.getCurrentDateUTC())) {
                    isActive = true;
                    break;
                }
            }
            studentEntry.setMembershipStatus(isActive ? MembershipStatus.ACTIVE : MembershipStatus.INACTIVE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentEntry;
    }

    private Member convertToEntity(StudentEntry studentEntry, Member existingStudent) throws Exception {
        Member student = (existingStudent != null) ? existingStudent : new Member();

        // add identifier as STUDENT
        student.setMemberType(MemberType.STUDENT.name());

        if (Objects.nonNull(studentEntry.getStudentId())) {
            student.setId(studentEntry.getStudentId());
        }
        if (Objects.nonNull(studentEntry.getName())) {
            student.setName(studentEntry.getName());
        }
        if (Objects.nonNull(studentEntry.getEmail())) {
            student.setEmail(studentEntry.getEmail());
        }
        if (Objects.nonNull(studentEntry.getPhone())) {
            student.setPhone(studentEntry.getPhone());
        }
        if (Objects.nonNull(studentEntry.getDob())) {
            student.setDob(studentEntry.getDob());
        }
        if (Objects.nonNull(studentEntry.getImageUrl())) {
            student.setProfileImage(studentEntry.getImageUrl());
        }
        if (Objects.nonNull(studentEntry.getAddress())) {
            student.setAddress(studentEntry.getAddress());
        }
        if (Objects.nonNull(studentEntry.getEmergencyContactNumber())) {
            student.setEmergencyContactNumber(studentEntry.getEmergencyContactNumber());
        }
        if (Objects.nonNull(studentEntry.getBranchId())) {
            BranchEntry entry = branchManager.getById(studentEntry.getBranchId());
            student.setBranch(ConvertToEntryUtil.convertToEntity(entry, null));
        }

        return student;
    }

    @Override
    public Long getAllStudentsCountByStudio(Long branchId) {
        return memberRepository.totalStudentsByBranchId(branchId);
    }
}
