package com.dancestudio.erp.modules.member.instructor.bankAccount;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.BankAccount;
import com.dancestudio.erp.repository.BankAccountRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

@Service
public class BankAccountManager extends BaseManager<BankAccount, Long, BankAccountEntry> {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountManager(BankAccountRepository bankAccountRepository) {
        super(bankAccountRepository, "BankAccount");
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccountEntry getByInstructorId(Long instructorId) throws EntityNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findByInstructorId(instructorId).orElse(null);
        return ConvertToEntryUtil.convertToEntry(bankAccount);
    }

    @Override
    protected BankAccount toEntity(BankAccountEntry entry, BankAccount existing)
            throws EntityNotFoundException, BeansException, Exception {
        return ConvertToEntryUtil.convertToEntity(entry, existing);
    }

    @Override
    protected BankAccountEntry toEntry(BankAccount entity, String[] fields) throws EntityNotFoundException {
        return ConvertToEntryUtil.convertToEntry(entity);
    }

}
