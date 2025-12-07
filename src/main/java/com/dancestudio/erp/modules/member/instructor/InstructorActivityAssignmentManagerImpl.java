package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InstructorActivityAssignmentManagerImpl implements InstructorActivityAssignmentManager {
    private final InstructorActivityAssignmentRepository instructorActivityAssignmentRepository;

    @Autowired
    private MemberRepository memberRepository;

    public InstructorActivityAssignmentManagerImpl(
            InstructorActivityAssignmentRepository instructorActivityAssignmentRepository) {
        this.instructorActivityAssignmentRepository = instructorActivityAssignmentRepository;
    }

    @Override
    public InstructorActivityAssignmentEntry add(InstructorActivityAssignmentEntry instructorActivityAssignmentEntry)
            throws Exception {
        memberRepository.findById(instructorActivityAssignmentEntry.getInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        InstructorActivityAssignment instructorActivityAssignment = InstructorActivityAssignmentConvertor
                .convertToEntity(instructorActivityAssignmentEntry, null);
        instructorActivityAssignment = instructorActivityAssignmentRepository.save(instructorActivityAssignment);

        return InstructorActivityAssignmentConvertor.convertToEntry(instructorActivityAssignment);
    }

    @Override
    public InstructorActivityAssignmentEntry update(Long instructorActivityAssignmentId,
            InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) throws Exception {
        InstructorActivityAssignment existingInstructorActivityAssignment = instructorActivityAssignmentRepository
                .findById(instructorActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        InstructorActivityAssignment updatedInstructorActivityAssignment = InstructorActivityAssignmentConvertor
                .convertToEntity(instructorActivityAssignmentEntry, existingInstructorActivityAssignment);
        updatedInstructorActivityAssignment = instructorActivityAssignmentRepository
                .save(updatedInstructorActivityAssignment);

        return InstructorActivityAssignmentConvertor
                .convertToEntry(instructorActivityAssignmentRepository.save(updatedInstructorActivityAssignment));
    }

    @Override
    public void delete(Long instructorActivityAssignmentId) throws EntityNotFoundException {
        instructorActivityAssignmentRepository.findById(instructorActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        instructorActivityAssignmentRepository.deleteById(instructorActivityAssignmentId);
    }

    @Override
    public InstructorActivityAssignmentEntry getById(Long instructorActivityAssignmentAssignmentId) throws Exception {
        InstructorActivityAssignment instructorActivityAssignment = instructorActivityAssignmentRepository
                .findById(instructorActivityAssignmentAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        return InstructorActivityAssignmentConvertor.convertToEntry(instructorActivityAssignment);
    }

    @Override
    public InstructorActivityAssignmentEntry getInstructorAssignmentsByInstructorAndActivityId(Long instructorId,
            String activityName) throws Exception {
        InstructorActivityAssignment assignment = instructorActivityAssignmentRepository
                .findByInstructorIdAndActivityId(instructorId, activityName);
        return InstructorActivityAssignmentConvertor.convertToEntry(assignment);
    }

    @Override
    public Page<InstructorActivityAssignment> getAssignmentsByInstructor(Long instructorId, Integer page, Integer size)
            throws Exception {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return instructorActivityAssignmentRepository.findByInstructorId(instructorId, pageable);
    }
}
