package com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.member.memberActiveStatus.MemberActiveStatusUtil;

import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InstructorActivityAssignmentManager
                extends BaseManager<InstructorActivityAssignment, Long, InstructorActivityAssignmentEntry> {

        private final MemberRepository memberRepository;
        private final InstructorActivityAssignmentRepository instructorActivityAssignmentRepository;

        public InstructorActivityAssignmentManager(
                        InstructorActivityAssignmentRepository instructorActivityAssignmentRepository,
                        MemberRepository memberRepository) {
                super(instructorActivityAssignmentRepository, "InstructorActivityAssignment");
                this.instructorActivityAssignmentRepository = instructorActivityAssignmentRepository;
                this.memberRepository = memberRepository;
        }

        @Override
        public InstructorActivityAssignmentEntry add(InstructorActivityAssignmentEntry entry)
                        throws EntityNotFoundException, BeansException, Exception {

                Member member = memberRepository.findById(entry.getInstructorId())
                                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

                InstructorActivityAssignmentEntry activityAssignmentEntry = super.add(entry);

                MemberActiveStatusUtil.addNewAssignment(member,
                                entry.getStartDate(),
                                entry.getEndDate());

                return activityAssignmentEntry;

        }

        @Override
        public void delete(Long id) throws EntityNotFoundException {
                InstructorActivityAssignment assignment = instructorActivityAssignmentRepository
                                .findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "InstructorActivityAssignment not found"));
                super.delete(id);
                try {
                        MemberActiveStatusUtil.deleteNewAssignment(assignment.getInstructor(),
                                        assignment.getStartDate(),
                                        assignment.getEndDate());
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        @Override
        public InstructorActivityAssignmentEntry update(Long id, InstructorActivityAssignmentEntry entry)
                        throws EntityNotFoundException, BeansException, Exception {
                InstructorActivityAssignment assignment = instructorActivityAssignmentRepository
                                .findById(entry.getAssignmentId()).orElseThrow(() -> new EntityNotFoundException(
                                                "InstructorActivityAssignment not found"));
                entry = super.update(id, entry);
                MemberActiveStatusUtil.updateNewAssignment(assignment.getInstructor(),
                                assignment.getStartDate(),
                                assignment.getEndDate(),
                                entry.getStartDate(),
                                entry.getEndDate());
                return entry;
        }

        public InstructorActivityAssignmentEntry getInstructorAssignmentsByInstructorAndActivityId(Long instructorId,
                        String activityName) throws Exception {
                InstructorActivityAssignment assignment = instructorActivityAssignmentRepository
                                .findByInstructorIdAndActivityId(instructorId, activityName);
                return InstructorActivityAssignmentConvertor.convertToEntry(assignment);
        }

        public Page<InstructorActivityAssignment> getAssignmentsByInstructor(Long instructorId, Integer page,
                        Integer size)
                        throws Exception {
                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

                return instructorActivityAssignmentRepository.findByInstructorId(instructorId, pageable);
        }

        @Override
        protected InstructorActivityAssignment toEntity(InstructorActivityAssignmentEntry entry,
                        InstructorActivityAssignment existing)
                        throws EntityNotFoundException, BeansException, Exception {
                return InstructorActivityAssignmentConvertor.convertToEntity(entry, existing);
        }

        @Override
        protected InstructorActivityAssignmentEntry toEntry(InstructorActivityAssignment entity)
                        throws EntityNotFoundException {
                return InstructorActivityAssignmentConvertor.convertToEntry(entity);
        }
}
