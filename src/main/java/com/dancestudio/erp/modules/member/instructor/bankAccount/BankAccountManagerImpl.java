package com.dancestudio.erp.modules.member.instructor.bankAccount;


import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.BankAccount;
import com.dancestudio.erp.repository.BankAccountRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountManagerImpl implements BankAccountManager {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountManagerImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccountEntry add(BankAccountEntry bankAccountEntry) throws EntityNotFoundException {
        BankAccount bankAccount = ConvertToEntryUtil.convertToEntity(bankAccountEntry, null);
        return ConvertToEntryUtil.convertToEntry(bankAccountRepository.save(bankAccount));
    }

    @Override
    public BankAccountEntry update(Long bankAccountId, BankAccountEntry bankAccountEntry) throws EntityNotFoundException {
        BankAccount existingBankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        BankAccount updatedBankAccount = ConvertToEntryUtil.convertToEntity(bankAccountEntry, existingBankAccount);
        return ConvertToEntryUtil.convertToEntry(bankAccountRepository.save(updatedBankAccount));
    }

    @Override
    public void delete(Long bankAccountId) throws EntityNotFoundException {
        bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        bankAccountRepository.deleteById(bankAccountId);
    }

    @Override
    public BankAccountEntry getById(Long bankAccountId) throws EntityNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        return ConvertToEntryUtil.convertToEntry(bankAccount);
    }

    @Override
    public BankAccountEntry getByInstructorId(Long instructorId) throws EntityNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findByInstructorId(instructorId).orElse(null);
        return ConvertToEntryUtil.convertToEntry(bankAccount);
    }

}
