package com.dancestudio.erp.modules.member.memberActiveStatus;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentRepository;

import lombok.Setter;

import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
public class MemberActiveStatusManager extends BaseManager<MemberActiveStatus, Long, MemberActiveStatusEntry> {

    private final MemberActiveStatusRepository repository;

    private final MemberRepository memberRepository;

    private final StudentActivityAssignmentRepository assignedRepo;

    @Autowired
    public MemberActiveStatusManager(MemberActiveStatusRepository repository, MemberRepository memberRepository,
            StudentActivityAssignmentRepository assignedRepo) {
        super(repository, "Member Active Status");
        this.repository = repository;
        this.memberRepository = memberRepository;
        this.assignedRepo = assignedRepo;
    }

    @Override
    protected MemberActiveStatus toEntity(MemberActiveStatusEntry entry, MemberActiveStatus existing)
            throws EntityNotFoundException, BeansException, Exception {
        MemberActiveStatus entity = (existing != null) ? existing : new MemberActiveStatus();
        Member member = memberRepository.findById(entry.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        entity.setMember(member);
        entity.setEarliestStartDate(entry.getEarliestStartDate());
        entity.setLatestEndDate(entry.getLatestEndDate());
        return entity;
    }

    @Override
    protected MemberActiveStatusEntry toEntry(MemberActiveStatus entity) throws EntityNotFoundException {
        MemberActiveStatusEntry entry = new MemberActiveStatusEntry();
        entry.setMemberId(entity.getId());
        entry.setEarliestStartDate(entity.getEarliestStartDate());
        entry.setLatestEndDate(entity.getLatestEndDate());

        return entry;
    }

    public boolean isMemberActive(Long memberId) {
        Date today = new Date();
        return repository.findById(memberId)
                .map(status -> {
                    Date start = status.getEarliestStartDate();
                    Date end = status.getLatestEndDate();

                    if (start == null)
                        return false;

                    boolean started = !today.before(start);

                    if (end == null)
                        return started;

                    boolean notEnded = !today.after(end);
                    return started && notEnded;
                })
                .orElse(false);
    }

    public MemberActiveStatusEntry rebuildWindowForMember(Long memberId)
            throws EntityNotFoundException, Exception {

        Object[] result = assignedRepo.findMinMaxWindow(memberId);

        
        if (result == null || result.length < 2) {
            repository.deleteById(memberId);
            return null;
        }

        Date earliest = (Date) result[0];
        Date latest = (Date) result[1];

        if (earliest == null || latest == null) {
            repository.deleteById(memberId);
            return null;
        }

        MemberActiveStatus entity = repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "MemberActiveStatus not found for memberId: " + memberId));

        entity.setEarliestStartDate(earliest);
        entity.setLatestEndDate(latest);

        entity = repository.save(entity);

        return toEntry(entity);
    }

}
