package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.repository.StudentActivityAssignmentRepository;
import com.dancestudio.erp.repository.StudentRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.dancestudio.erp.constants.TemplateName.ADD_NEW_STUDENT_EMAIL;
import static com.dancestudio.erp.constants.TemplateName.UPDATE_STUDENT_EMAIL;

@Service
@Slf4j
public class StudentManagerImpl implements StudentManager {

    private final StudentRepository studentRepository;
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;

    @Autowired
    private EmailManager emailManager;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    private TemplateManager templateManager;

    @Autowired
    private StudentActivityAssignmentManager studentActivityAssignmentManager;

    @Autowired
    public StudentManagerImpl(StudentRepository studentRepository, StudentActivityAssignmentRepository studentActivityAssignmentRepository) {
        this.studentRepository = studentRepository;
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
    }

    @Override
    public StudentEntry addStudent(StudentEntry studentEntry) throws Exception {
        if (studentRepository.findByNameAndEmail(studentEntry.getName(), studentEntry.getEmail()).isPresent()) {
            throw new Exception("Student already exists");
        }

        Student student = convertToEntity(studentEntry, null);
        student = studentRepository.save(student);

        TemplateEntry templateEntry = templateManager.getTemplateDetails(ADD_NEW_STUDENT_EMAIL);
        emailManager.sendEmail(student.getEmail(), templateEntry.getSubject(), templateEntry.getTemplateBody());
        return convertToEntry(student);
    }

    @Override
    public StudentEntry updateStudent(Long studentId, StudentEntry studentEntry) throws EntityNotFoundException {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Student updatedStudentEntry = convertToEntity(studentEntry, existingStudent);
        updatedStudentEntry = studentRepository.save(updatedStudentEntry);

        TemplateEntry templateEntry = templateManager.getTemplateDetails(UPDATE_STUDENT_EMAIL);
        emailManager.sendEmail(updatedStudentEntry.getEmail(), templateEntry.getSubject(), templateEntry.getTemplateBody());

        return convertToEntry(updatedStudentEntry);
    }

    @Override
    public void deleteStudent(Long studentId) throws EntityNotFoundException {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        studentRepository.deleteById(studentId);
    }

    @Override
    public StudentEntry getStudentById(Long studentId) throws EntityNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return convertToEntry(student);
    }

    @Override
    public List<StudentEntry> getAllStudentsByStudio(Long studioId, Long activityId, MembershipStatus membershipStatus, int page, int size) {
        if (size == -1) {
            List<Student> entries = studentRepository.findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(studioId, activityId, membershipStatus);
            return entries.stream()
                    .map(this::convertToEntry)
                    .collect(Collectors.toList());
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Student> studentPage = studentRepository.findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(studioId, activityId, membershipStatus, pageable);
            return studentPage.getContent().stream()
                    .map(this::convertToEntry)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<StudentEntry> findByMembershipEndDate(LocalDate reminderDate) {
        List<Long> studentIds = studentActivityAssignmentRepository.findStudentIdsWithMembershipEndingOnDate(reminderDate);

        List<StudentEntry> studentEntries = new ArrayList<>();
        for (Long studentId : studentIds) {
            try {
                StudentEntry studentEntry = getStudentById(studentId);
                studentEntries.add(studentEntry);
            } catch (EntityNotFoundException ex) {
                log.error("Entity not found : {}", ex.getMessage());
            }
        }
        return studentEntries;
    }

    @Override
    public boolean sendSubscriptionRenewalReminder(Long studentId, Long activityId) throws EntityNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        StudentActivityAssignmentEntry entry = studentActivityAssignmentManager.getStudentAssignmentsByStudentAndActivityId(studentId, activityId);

        if (Objects.isNull(entry)) {
            throw new EntityNotFoundException("No such entries found");
        }

        StudioEntry studioEntry = studioManager.getStudioById(entry.getActivity().getStudioId());
        emailManager.sendSubscriptionRenewalEmail(student, entry, studioEntry.getStudioName());
        return true;
    }

    @Override
    public List<StudentEntry> getStudentByActivityIdAndStudioIdAndStatus(Long activityId, Long studioId, String status) {
        List<Student> entries = studentActivityAssignmentRepository.findByOptionalActivityIdAndStudioIdAndStatus(activityId, studioId, status);

        List<StudentEntry> studentEntries = new ArrayList<>();
        for (Student entry : entries) {
            StudentEntry studentEntry = convertToEntry(entry);
            studentEntries.add(studentEntry);
        }

        return studentEntries;
    }

    public Boolean checkIfStudentExistsinStudio(Long studioId) {
        return studentRepository.studentsExistsByStudioId(studioId);
    }

    private StudentEntry convertToEntry(Student student) {

        StudentEntry studentEntry = new StudentEntry();
        studentEntry.setStudentId(student.getId());
        studentEntry.setName(student.getName());
        studentEntry.setPhone(student.getPhone());
        studentEntry.setEmail(student.getEmail());
        studentEntry.setImageUrl(student.getProfileImage());
        studentEntry.setMembershipStatus(MembershipStatus.valueOf(student.getStatus()));

        try {
            StudioEntry entry = studioManager.getStudioById(student.getStudio().getId());
            studentEntry.setStudioId(entry.getStudioId());
        } catch (Exception ex) {
            studentEntry.setStudioId(null);
        }

        return studentEntry;
    }

    private Student convertToEntity(StudentEntry studentEntry, Student existingStudent) throws EntityNotFoundException {
        Student student = (existingStudent != null) ? existingStudent : new Student();

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
        if (Objects.nonNull(studentEntry.getImageUrl())) {
            student.setProfileImage(studentEntry.getImageUrl());
        }
        if (Objects.nonNull(studentEntry.getMembershipStatus())) {
            student.setStatus(studentEntry.getMembershipStatus().name());
        }
        if (Objects.nonNull(studentEntry.getStudioId())) {
            StudioEntry entry = studioManager.getStudioById(studentEntry.getStudioId());
            student.setStudio(ConvertToEntryUtil.convertToEntity(entry, null));
        }

        return student;
    }
}
