package com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.exception.EntityNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InstructorActivityAssignmentManager
                extends BaseManager<InstructorActivityAssignment, Long, InstructorActivityAssignmentEntry> {
        private final InstructorActivityAssignmentRepository instructorActivityAssignmentRepository;

        public InstructorActivityAssignmentManager(
                        InstructorActivityAssignmentRepository instructorActivityAssignmentRepository) {
                super(instructorActivityAssignmentRepository, "InstructorActivityAssignment");
                this.instructorActivityAssignmentRepository = instructorActivityAssignmentRepository;
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
