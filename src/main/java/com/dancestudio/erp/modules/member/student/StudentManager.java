package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.base.BaseManager;
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
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentEntry;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentManager;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentRepository;
import com.dancestudio.erp.modules.message_queue.services.EmailService;
import com.dancestudio.erp.modules.template.template.TemplateEntry;
import com.dancestudio.erp.modules.template.template.TemplateManager;
import com.dancestudio.erp.repository.MessageRepository;
import com.dancestudio.erp.util.DateUtil;
import com.dancestudio.erp.util.WhatsappUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import static com.dancestudio.erp.constants.TemplateName.ADD_NEW_STUDENT_EMAIL;

@Service
@Slf4j
@Setter
@Transactional(rollbackFor = Exception.class)
public class StudentManager extends BaseManager<Member, Long, StudentEntry> {

    private final EmailService emailService;

    private final MemberRepository memberRepository;
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;
    private final MessageRepository messageRepository;

    @Autowired
    private NotificationManager notificationManager;
    @Autowired
    private StudioManager studioManager;
    @Autowired
    private BranchManager branchManager;
    @Autowired
    private TemplateManager templateManager;
    @Autowired
    private StudentActivityAssignmentManager studentActivityAssignmentManager;
    @Autowired
    private WhatsappUtil whatsappUtil;

    public StudentManager(MemberRepository memberRepository,
            StudentActivityAssignmentRepository studentActivityAssignmentRepository, EmailService emailService,
            MessageRepository messageRepository) {
        super(memberRepository, "Student");
        this.memberRepository = memberRepository;
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
        this.emailService = emailService;
        this.messageRepository = messageRepository;
    }

    @Override
    public StudentEntry add(StudentEntry studentEntry) throws Exception {
        if (memberRepository.findByNameAndMemberTypeAndEmail(studentEntry.getName(), MemberType.STUDENT.name(),
                studentEntry.getEmail()).isPresent()) {
            throw new Exception("Student already exists");
        }

        Member member = StudentConvertor.convertToEntity(studentEntry, null);
        member = memberRepository.save(member);

        TemplateEntry templateEntry = templateManager.getTemplateDetails(ADD_NEW_STUDENT_EMAIL);

        BranchEntry branchEntry = branchManager.getById(member.getBranch().getId());
        StudioEntry studioEntry = studioManager.getById(branchEntry.getStudioId());

        String updatedBody = formatEmailBody(studioEntry, templateEntry, member);
        if (Objects.nonNull(studioEntry.getPasscode()) && Objects.nonNull(studioEntry.getEmail())) {

            Message message = messageRepository.save(new Message(member.getBranch(), false,
                    templateEntry.getSubject(), templateEntry.getTemplateBody(),
                    NotificationType.EMAIL.name()));
            MessageRecipient recepient = new MessageRecipient(message, member.getName(), member.getEmail(),
                    MessageStatus.PENDING, null);
            emailService.sendHighPriorityEmail(member.getEmail(), templateEntry.getSubject(), updatedBody,
                    branchEntry.getStudioId(), null, null, recepient);
        }

        return StudentConvertor.convertToEntry(member);
    }

    public Page<StudentEntry> getAllStudentsByStudio(Long branchId, String activityName,
            MembershipStatus membershipStatus, int page, int size, String searchTerm, Boolean isActive) {

        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
        Page<Member> studentPage = memberRepository
                .findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTermAndIsActive(
                        branchId, activityName, membershipStatus != null ? membershipStatus.name() : null, pageable,
                        searchTerm, isActive);

        if (membershipStatus == null) {
            return studentPage.map(StudentConvertor::convertToEntry);
        }

        return studentPage.map(StudentConvertor::convertToEntry);
    }

    public Page<StudentCommunicationEntry> getAllStudentsForCommunication(Long branchId,
            MembershipStatus membershipStatus, int page, int size, int birthday) {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
        Page<Member> studentPage;
        if (birthday == 1) {
            LocalDate today = DateUtil.getToday();
            studentPage = memberRepository.findByDobMonthDay(today.getMonthValue(), today.getDayOfMonth(), branchId,
                    pageable);
        } else {
            studentPage = memberRepository
                    .findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTermAndIsActive(branchId,
                            null, membershipStatus.name(), pageable, null, true);
        }
        return studentPage.map(this::convertToEntryComm);
    }

    protected StudentCommunicationEntry convertToEntryComm(Member instructor) {
        StudentCommunicationEntry entry = new StudentCommunicationEntry();
        entry.setStudentId(instructor.getId());
        entry.setName(instructor.getName());
        return entry;
    }

    public List<StudentEntry> findByMembershipEndDate(Date reminderDate) {
        List<Long> studentIds = studentActivityAssignmentRepository
                .findStudentIdsWithMembershipEndingOnDate(reminderDate);

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

    public boolean sendSubscriptionRenewalReminder(Long studentId, String activityName) throws Exception {
        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        StudentActivityAssignmentEntry entry = studentActivityAssignmentManager
                .getStudentAssignmentsByStudentAndActivityId(studentId, activityName);
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

    @Override
    protected Member toEntity(StudentEntry entry, Member existing)
            throws EntityNotFoundException, BeansException, Exception {
        return StudentConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected StudentEntry toEntry(Member entity, String[] fields) throws EntityNotFoundException {
        return StudentConvertor.convertToEntry(entity);
    }
}
