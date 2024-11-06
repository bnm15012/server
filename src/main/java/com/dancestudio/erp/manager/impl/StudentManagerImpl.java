package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EmailManager;
import com.dancestudio.erp.manager.StudentActivityAssignmentManager;
import com.dancestudio.erp.manager.StudentManager;
import com.dancestudio.erp.repository.StudentActivityAssignmentRepository;
import com.dancestudio.erp.repository.StudentRepository;
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

@Service
@Slf4j
public class StudentManagerImpl implements StudentManager {

    private final StudentRepository studentRepository;
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;

    @Autowired
    private EmailManager emailManager;

    @Autowired
    private StudentActivityAssignmentManager studentActivityAssignmentManager;

    @Autowired
    public StudentManagerImpl(StudentRepository studentRepository, StudentActivityAssignmentRepository studentActivityAssignmentRepository) {
        this.studentRepository = studentRepository;
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
    }

    @Override
    public StudentEntry addStudent(StudentEntry studentEntry) throws Exception {
        Student student = convertToEntity(studentEntry, null);
        student = studentRepository.save(student);

        emailManager.sendRegistrationEmail(student);
        return convertToEntry(student);
    }

    @Override
    public StudentEntry updateStudent(Long studentId, StudentEntry studentEntry) throws EntityNotFoundException {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Student updatedStudentEntry = convertToEntity(studentEntry, existingStudent);
        return convertToEntry(studentRepository.save(updatedStudentEntry));
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
        List<Student> entries = studentRepository.findStudentsWithMembershipEndingOnDate(reminderDate);

        List<StudentEntry> studentEntries = new ArrayList<>();
        for (Student entry : entries) {
            StudentEntry studentEntry = convertToEntry(entry);
            studentEntries.add(studentEntry);
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
        emailManager.sendSubscriptionRenewalEmail(student, entry);
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
        studentEntry.setImageUrl(student.getProfileImage());
        studentEntry.setMembershipStatus(MembershipStatus.valueOf(student.getStatus()));
        studentEntry.setStudioId(student.getStudioId());

        if (Objects.nonNull(student.getEnrolledActivityIdList())) {
            List<Long> activityIds = student.getEnrolledActivityIdList();
            List<StudentActivityAssignmentEntry> activityAssignmentEntries = new ArrayList<>();

            try {
                for (Long id : activityIds) {
                    StudentActivityAssignmentEntry studentActivityAssignmentEntry = studentActivityAssignmentManager.getStudentActivityAssignmentById(id);
                    activityAssignmentEntries.add(studentActivityAssignmentEntry);
                }
                studentEntry.setEnrolledActivities(activityAssignmentEntries);
            } catch (EntityNotFoundException ex) {
                log.info("No Student Activity Assignment Entry found for given student");
                studentEntry.setEnrolledActivities(null);
            }
        }

        return studentEntry;
    }

    private Student convertToEntity(StudentEntry studentEntry, Student existingStudent) {
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
            student.setStudioId(studentEntry.getStudioId());
        }
        if (Objects.nonNull(studentEntry.getEnrolledActivities())) {
            List<Long> ids = studentEntry.getEnrolledActivityIds();
            student.setEnrolledActivityIds(ids);
        }

        return student;
    }
}
