package com.dancestudio.erp.modules.member.memberActiveStatus;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment.InstructorActivityAssignment;
import com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment.InstructorActivityAssignmentRepository;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignment;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentRepository;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter
@Transactional(rollbackFor = Exception.class)
public class MemberActiveStatusManager extends BaseManager<MemberActiveStatus, Long, MemberActiveStatusEntry> {

    private final MemberActiveStatusRepository repository;

    private final MemberRepository memberRepository;

    private final StudentActivityAssignmentRepository assignedRepo;

    private final InstructorActivityAssignmentRepository activityAssignmentRepository;

    public MemberActiveStatusManager(MemberActiveStatusRepository repository, MemberRepository memberRepository,
            StudentActivityAssignmentRepository assignedRepo,
            InstructorActivityAssignmentRepository activityAssignmentRepository) {
        super(repository, "Member Active Status");
        this.repository = repository;
        this.memberRepository = memberRepository;
        this.assignedRepo = assignedRepo;
        this.activityAssignmentRepository = activityAssignmentRepository;
    }

    @Override
    protected MemberActiveStatus toEntity(MemberActiveStatusEntry entry, MemberActiveStatus existing)
            throws EntityNotFoundException, Exception {
        MemberActiveStatus entity = (existing != null) ? existing : new MemberActiveStatus();
        Member member = memberRepository.findById(entry.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        entity.setMember(member);
        entity.setActivePeriods(entry.getActivePeriods());
        return entity;
    }

    @Override
    protected MemberActiveStatusEntry toEntry(MemberActiveStatus entity, String[] fields)
            throws EntityNotFoundException {
        MemberActiveStatusEntry entry = new MemberActiveStatusEntry();
        entry.setMemberId(entity.getId());
        entry.setActivePeriods(entity.getActivePeriods());
        return entry;
    }

    public MemberActiveStatusEntry findByMemberId(Long memberId) {
        return repository.findById(memberId)
                .map(entity -> {
                    try {
                        return toEntry(entity, new String[] {});
                    } catch (EntityNotFoundException e) {
                        return null;
                    }
                })
                .orElse(null);
    }

    public MembershipStatus getMembershipStatus(Long memberId) {
        return repository.findById(memberId)
                .map(status -> {
                    List<ActivePeriod> periods = status.getActivePeriods();
                    return getMembershipStatus(periods);
                })
                .orElse(MembershipStatus.INACTIVE);
    }

    public MemberActiveStatusEntry rebuildPeriodsForMember(Long memberId, String memberType)
            throws EntityNotFoundException, Exception {

        List<ActivePeriod> periods = loadPeriodsFromAssignments(memberId, memberType);

        periods = MemberActiveStatusUtil.mergePeriods(periods);
        periods = MemberActiveStatusUtil.removeExpiredPeriods(periods);

        if (periods.isEmpty()) {
            if (repository.existsById(memberId)) {
                repository.deleteById(memberId);
            }
            return null;
        }

        MemberActiveStatus entity = repository.findById(memberId).orElse(null);
        if (entity == null) {
            MemberActiveStatusEntry newEntry = new MemberActiveStatusEntry(memberId, periods);
            return this.add(newEntry);
        }

        entity.setActivePeriods(periods);
        entity = repository.save(entity);
        return toEntry(entity, new String[] {});
    }

    private List<ActivePeriod> loadPeriodsFromAssignments(Long memberId, String memberType) {
        List<ActivePeriod> periods = new ArrayList<>();

        if (memberType.equals(MemberType.STUDENT.toString())) {
            List<StudentActivityAssignment> assignments = assignedRepo.findByStudentId(memberId);
            for (StudentActivityAssignment a : assignments) {
                periods.add(new ActivePeriod(a.getMembershipStartDate(), a.getMembershipEndDate()));
            }
        } else {
            List<InstructorActivityAssignment> assignments =
                    activityAssignmentRepository.findByInstructorId(memberId, Pageable.unpaged()).getContent();
            for (InstructorActivityAssignment a : assignments) {
                periods.add(new ActivePeriod(a.getStartDate(), a.getEndDate()));
            }
        }

        return periods;
    }

    private MembershipStatus getMembershipStatus(List<ActivePeriod> periods) {
        if (periods == null || periods.isEmpty()) {
            return MembershipStatus.INACTIVE;
        }
        for (ActivePeriod period : periods) {
            if (MemberActiveStatusUtil.isMembershipActive(period.getStartDate(), period.getEndDate())) {
                return MembershipStatus.ACTIVE;
            }
        }
        return MembershipStatus.INACTIVE;
    }
}
