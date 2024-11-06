package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entity.StudentActivityAssignment;
import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.enums.MembershipType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.manager.StudentActivityAssignmentManager;
import com.dancestudio.erp.repository.StudentActivityAssignmentRepository;
import com.dancestudio.erp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StudentActivityAssignmentManagerImpl implements StudentActivityAssignmentManager {
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;

    @Autowired
    private ActivityManager activityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    public StudentActivityAssignmentManagerImpl(StudentActivityAssignmentRepository studentActivityAssignmentRepository) {
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
    }

    @Override
    public StudentActivityAssignmentEntry addStudentActivityAssignment(StudentActivityAssignmentEntry studentStudentActivityAssignmentAssignmentEntry) throws EntityNotFoundException {
        StudentActivityAssignment studentStudentActivityAssignmentAssignment = convertToEntity(studentStudentActivityAssignmentAssignmentEntry, null);
        studentStudentActivityAssignmentAssignment = studentActivityAssignmentRepository.save(studentStudentActivityAssignmentAssignment);

        addAssignmentToStudent(studentStudentActivityAssignmentAssignmentEntry.getStudentId(), studentStudentActivityAssignmentAssignment.getId());
        return convertToEntry(studentStudentActivityAssignmentAssignment);
    }

    @Override
    public StudentActivityAssignmentEntry updateStudentActivityAssignment(Long studentStudentActivityAssignmentAssignmentId, StudentActivityAssignmentEntry studentStudentActivityAssignmentAssignmentEntry) throws EntityNotFoundException {
        StudentActivityAssignment existingStudentActivityAssignment = studentActivityAssignmentRepository.findById(studentStudentActivityAssignmentAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        StudentActivityAssignment updatedStudentActivityAssignment = convertToEntity(studentStudentActivityAssignmentAssignmentEntry, existingStudentActivityAssignment);
        updatedStudentActivityAssignment = studentActivityAssignmentRepository.save(updatedStudentActivityAssignment);

        addAssignmentToStudent(studentStudentActivityAssignmentAssignmentEntry.getStudentId(), updatedStudentActivityAssignment.getId());
        return convertToEntry(studentActivityAssignmentRepository.save(updatedStudentActivityAssignment));
    }

    @Override
    public void deleteStudentActivityAssignment(Long studentStudentActivityAssignmentAssignmentId) throws EntityNotFoundException {
        studentActivityAssignmentRepository.findById(studentStudentActivityAssignmentAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        studentActivityAssignmentRepository.deleteById(studentStudentActivityAssignmentAssignmentId);
    }

    @Override
    public StudentActivityAssignmentEntry getStudentActivityAssignmentById(Long studentStudentActivityAssignmentAssignmentId) throws EntityNotFoundException {
        StudentActivityAssignment studentStudentActivityAssignmentAssignment = studentActivityAssignmentRepository.findById(studentStudentActivityAssignmentAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        return convertToEntry(studentStudentActivityAssignmentAssignment);
    }

    @Override
    public StudentActivityAssignmentEntry getStudentAssignmentsByStudentAndActivityId(Long studentId, Long activityId) throws EntityNotFoundException {
        StudentActivityAssignment assignment = studentActivityAssignmentRepository.findByStudentIdAndActivityId(studentId, activityId);
        return convertToEntry(assignment);
    }

    private void addAssignmentToStudent(Long studentId, Long assignmentId) throws EntityNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        student.addEnrolledActivityId(assignmentId);
        studentRepository.save(student);
    }

    private StudentActivityAssignmentEntry convertToEntry(StudentActivityAssignment studentActivityAssignment) throws EntityNotFoundException {

        if (Objects.isNull(studentActivityAssignment)) {
            return null;
        }

        StudentActivityAssignmentEntry studentActivityAssignmentEntry = new StudentActivityAssignmentEntry();
        studentActivityAssignmentEntry.setAssignmentId(studentActivityAssignment.getId());
        studentActivityAssignmentEntry.setStudentId(studentActivityAssignment.getStudent().getId());
        studentActivityAssignmentEntry.setRegistrationDate(studentActivityAssignment.getRegistrationDate());
        studentActivityAssignmentEntry.setMembershipStartDate(studentActivityAssignment.getMembershipStartDate());
        studentActivityAssignmentEntry.setMembershipEndDate(studentActivityAssignment.getMembershipEndDate());
        studentActivityAssignmentEntry.setMembershipStatus(MembershipStatus.valueOf(studentActivityAssignment.getStatus()));
        studentActivityAssignmentEntry.setMembershipType(MembershipType.valueOf(studentActivityAssignment.getMembershipType()));

        if (Objects.nonNull(studentActivityAssignment.getActivityId())) {
            ActivityEntry activityEntry = activityManager.getActivityById(studentActivityAssignment.getActivityId());
            studentActivityAssignmentEntry.setActivity(activityEntry);
        }
        return studentActivityAssignmentEntry;
    }

    private StudentActivityAssignment convertToEntity(StudentActivityAssignmentEntry studentActivityAssignmentEntry, StudentActivityAssignment existingStudentActivityAssignment) throws EntityNotFoundException {
        StudentActivityAssignment studentActivityAssignment = (existingStudentActivityAssignment != null) ? existingStudentActivityAssignment : new StudentActivityAssignment();

        if (Objects.nonNull(studentActivityAssignmentEntry.getAssignmentId())) {
            studentActivityAssignment.setId(studentActivityAssignmentEntry.getAssignmentId());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getRegistrationDate())) {
            studentActivityAssignment.setRegistrationDate(studentActivityAssignmentEntry.getRegistrationDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipStartDate())) {
            studentActivityAssignment.setMembershipStartDate(studentActivityAssignmentEntry.getMembershipStartDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipEndDate())) {
            studentActivityAssignment.setMembershipEndDate(studentActivityAssignmentEntry.getMembershipEndDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipStatus())) {
            studentActivityAssignment.setStatus(studentActivityAssignmentEntry.getMembershipStatus().name());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipType())) {
            studentActivityAssignment.setMembershipType(studentActivityAssignmentEntry.getMembershipType().name());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getStudentId())) {
            Student student = studentRepository.findById(studentActivityAssignmentEntry.getStudentId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            studentActivityAssignment.setStudent(student);
        }

        if (Objects.nonNull(studentActivityAssignmentEntry.getActivity()) && Objects.nonNull(studentActivityAssignmentEntry.getActivity().getActivityId())) {
            ActivityEntry activityEntry = activityManager.getActivityById(studentActivityAssignmentEntry.getActivity().getActivityId());
            studentActivityAssignment.setActivityId(activityEntry.getActivityId());
        }

        return studentActivityAssignment;
    }
}
