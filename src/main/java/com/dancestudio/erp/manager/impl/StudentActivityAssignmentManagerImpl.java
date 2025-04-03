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
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StudentActivityAssignmentManagerImpl implements StudentActivityAssignmentManager {
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;

    @Autowired
    private ActivityManager activityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    public StudentActivityAssignmentManagerImpl(
            StudentActivityAssignmentRepository studentActivityAssignmentRepository) {
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
    }

    @Override
    public StudentActivityAssignmentEntry addStudentActivityAssignment(
            StudentActivityAssignmentEntry studentActivityAssignmentEntry) throws EntityNotFoundException {
        studentRepository.findById(studentActivityAssignmentEntry.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        StudentActivityAssignment studentStudentActivityAssignmentAssignment = convertToEntity(
                studentActivityAssignmentEntry, null);
        studentStudentActivityAssignmentAssignment = studentActivityAssignmentRepository
                .save(studentStudentActivityAssignmentAssignment);

        return convertToEntry(studentStudentActivityAssignmentAssignment);
    }

    @Override
    public StudentActivityAssignmentEntry updateStudentActivityAssignment(Long studentActivityAssignmentId,
            StudentActivityAssignmentEntry studentActivityAssignmentEntry) throws EntityNotFoundException {
        StudentActivityAssignment existingStudentActivityAssignment = studentActivityAssignmentRepository
                .findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        StudentActivityAssignment updatedStudentActivityAssignment = convertToEntity(studentActivityAssignmentEntry,
                existingStudentActivityAssignment);
        updatedStudentActivityAssignment = studentActivityAssignmentRepository.save(updatedStudentActivityAssignment);

        return convertToEntry(studentActivityAssignmentRepository.save(updatedStudentActivityAssignment));
    }

    @Override
    public void deleteStudentActivityAssignment(Long studentActivityAssignmentId) throws EntityNotFoundException {
        studentActivityAssignmentRepository.findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        studentActivityAssignmentRepository.deleteById(studentActivityAssignmentId);
    }

    @Override
    public StudentActivityAssignmentEntry getStudentActivityAssignmentById(Long studentActivityAssignmentId)
            throws EntityNotFoundException {
        StudentActivityAssignment studentActivityAssignment = studentActivityAssignmentRepository
                .findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        return convertToEntry(studentActivityAssignment);
    }

    @Override
    public StudentActivityAssignmentEntry getStudentAssignmentsByStudentAndActivityId(Long studentId, Long activityId)
            throws EntityNotFoundException {
        StudentActivityAssignment assignment = studentActivityAssignmentRepository
                .findByStudentIdAndActivityId(studentId, activityId);
        return convertToEntry(assignment);
    }

    @Override
    public List<StudentActivityAssignmentEntry> getStudentAssignmentsByStudentId(Long studentId)
            throws EntityNotFoundException {
        List<StudentActivityAssignment> enrollments = studentActivityAssignmentRepository.findByStudentId(studentId);
        List<StudentActivityAssignmentEntry> entries = new ArrayList<>();

        for (StudentActivityAssignment enrollment : enrollments) {
            entries.add(convertToEntry(enrollment));
        }

        return entries;
    }

    @Override
    public List<StudentActivityAssignmentEntry> getStudentByActivityIdAndStudioIdAndStatus(Long activityId,
            Long studioId, String status) throws EntityNotFoundException {

        List<StudentActivityAssignment> entries = studentActivityAssignmentRepository
                .findStudentsWithActiveMemberships(activityId, studioId);
        List<StudentActivityAssignmentEntry> assignmentEntries = new ArrayList<>();

        for (StudentActivityAssignment entry : entries) {
            boolean isActive = entry.getMembershipEndDate().after(DateUtil.getCurrentDateUTC());
            if ((status.equalsIgnoreCase("ACTIVE") && isActive) || (status.equalsIgnoreCase("INACTIVE") && !isActive)) {
                assignmentEntries.add(convertToEntry(entry));
            }
        }
        return assignmentEntries;
    }

    private StudentActivityAssignmentEntry convertToEntry(StudentActivityAssignment studentActivityAssignment)
            throws EntityNotFoundException {

        if (Objects.isNull(studentActivityAssignment)) {
            return null;
        }

        StudentActivityAssignmentEntry studentActivityAssignmentEntry = new StudentActivityAssignmentEntry();
        studentActivityAssignmentEntry.setAssignmentId(studentActivityAssignment.getId());
        studentActivityAssignmentEntry.setStudentId(studentActivityAssignment.getStudent().getId());
        studentActivityAssignmentEntry.setRegistrationDate(studentActivityAssignment.getRegistrationDate());
        studentActivityAssignmentEntry.setMembershipStartDate(studentActivityAssignment.getMembershipStartDate());
        studentActivityAssignmentEntry.setMembershipEndDate(studentActivityAssignment.getMembershipEndDate());
        studentActivityAssignmentEntry.setMembershipStatus(studentActivityAssignment.getMembershipEndDate().after(DateUtil.getCurrentDateUTC()) ? MembershipStatus.ACTIVE : MembershipStatus.INACTIVE);
        studentActivityAssignmentEntry.setMembershipType(MembershipType.valueOf(studentActivityAssignment.getMembershipType()));

        if (Objects.nonNull(studentActivityAssignment.getActivity())) {
            ActivityEntry activityEntry = activityManager.getActivityById(studentActivityAssignment.getActivity().getId());
            studentActivityAssignmentEntry.setActivity(activityEntry);
        }
        return studentActivityAssignmentEntry;
    }

    private StudentActivityAssignment convertToEntity(StudentActivityAssignmentEntry studentActivityAssignmentEntry,
            StudentActivityAssignment existingStudentActivityAssignment) throws EntityNotFoundException {
        StudentActivityAssignment studentActivityAssignment = (existingStudentActivityAssignment != null)
                ? existingStudentActivityAssignment
                : new StudentActivityAssignment();

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
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipType())) {
            studentActivityAssignment.setMembershipType(studentActivityAssignmentEntry.getMembershipType().name());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getStudentId())) {
            Student student = studentRepository.findById(studentActivityAssignmentEntry.getStudentId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            studentActivityAssignment.setStudent(student);
        }

        if (Objects.nonNull(studentActivityAssignmentEntry.getActivity())
                && Objects.nonNull(studentActivityAssignmentEntry.getActivity().getActivityId())) {
            ActivityEntry activityEntry = activityManager
                    .getActivityById(studentActivityAssignmentEntry.getActivity().getActivityId());
            studentActivityAssignment.setActivity(ConvertToEntryUtil.convertToEntity(activityEntry, null));
        }

        return studentActivityAssignment;
    }
}
