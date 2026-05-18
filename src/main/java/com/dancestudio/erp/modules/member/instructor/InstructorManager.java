package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.member.instructor.bankAccount.BankAccountEntry;
import com.dancestudio.erp.modules.member.instructor.bankAccount.BankAccountManager;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class InstructorManager extends BaseManager<Member, Long, InstructorEntry> {
    private final MemberRepository memberRepository;

    @Autowired
    private BankAccountManager bankAccountManager;

    public InstructorManager(MemberRepository memberRepository) {
        super(memberRepository, "Instructor");
        this.memberRepository = memberRepository;
    }

    @Override
    public InstructorEntry add(InstructorEntry instructorEntry) throws Exception {
        if (memberRepository.findByNameAndMemberTypeAndEmail(instructorEntry.getName(), MemberType.INSTRUCTOR.name(),
                instructorEntry.getEmail()).isPresent()) {
            throw new Exception("Instructor already exists");
        }

        Member instructor = InstructorConvertor.convertToEntity(instructorEntry, null);
        instructor = memberRepository.save(instructor);

        if (Objects.nonNull(instructorEntry.getBankAccountDetails())) {
            instructorEntry.getBankAccountDetails().setInstructorId(instructor.getId());
            bankAccountManager.add(instructorEntry.getBankAccountDetails());
        }

        return InstructorConvertor.convertToEntry(instructor);
    }

    @Override
    public InstructorEntry update(Long instructorId, InstructorEntry instructorEntry) throws Exception {
        Member existingInstructor = memberRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        if (Objects.nonNull(instructorEntry.getBankAccountDetails())) {
            instructorEntry.getBankAccountDetails().setInstructorId(existingInstructor.getId());

            BankAccountEntry bankAccountEntry = null;
            try {
                bankAccountEntry = bankAccountManager.getByInstructorId(existingInstructor.getId());
            } catch (Exception ignored) {
            }

            if (Objects.nonNull(bankAccountEntry)) {
                bankAccountManager.update(instructorEntry.getBankAccountDetails().getBankAccountId(),
                        instructorEntry.getBankAccountDetails());
            } else {
                bankAccountManager.add(instructorEntry.getBankAccountDetails());
            }
        }

        Member updatedInstructor = InstructorConvertor.convertToEntity(instructorEntry, existingInstructor);
        updatedInstructor = memberRepository.save(updatedInstructor);
        return InstructorConvertor.convertToEntry(updatedInstructor);
    }

    public Page<InstructorEntry> getAllInstructorsByBranch(Long branchId, MembershipStatus membershipStatus, int page,
            int size, String searchTerm) {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
        Page<Member> instructorPage = memberRepository
                .findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, null,
                        membershipStatus != null ? membershipStatus.name() : null, pageable, searchTerm);
        if (membershipStatus == null) {
            return instructorPage.map(InstructorConvertor::convertToEntry);
        }
        return instructorPage.map(InstructorConvertor::convertToEntry);
    }

    public Page<InstructorCommunicationEntry> getAllInstructorsForCommunication(Long branchId,
            MembershipStatus membershipStatus, int page, int size) {

        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
        Page<Member> instructorPage = memberRepository
                .findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, null,
                        membershipStatus.name(), pageable, null);

        return instructorPage.map(this::convertToEntryComm);
    }

    protected InstructorCommunicationEntry convertToEntryComm(Member instructor) {
        InstructorCommunicationEntry entry = new InstructorCommunicationEntry();
        entry.setInstructorId(instructor.getId());
        entry.setName(instructor.getName());
        return entry;
    }

    @Override
    protected Member toEntity(InstructorEntry entry, Member existing)
            throws EntityNotFoundException, BeansException, Exception {
        return InstructorConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected InstructorEntry toEntry(Member entity, String[] fields) throws EntityNotFoundException {
        return InstructorConvertor.convertToEntry(entity);
    }
}
