package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.BankAccount;
import com.dancestudio.erp.entry.BankAccountEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BankAccountManager;
import com.dancestudio.erp.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BankAccountManagerImpl implements BankAccountManager {
    private final BankAccountRepository bankAccountRepository;


    @Autowired
    public BankAccountManagerImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccountEntry addBankAccount(BankAccountEntry bankAccountEntry) throws EntityNotFoundException {
        BankAccount bankAccount = convertToEntity(bankAccountEntry, null);
        return convertToEntry(bankAccountRepository.save(bankAccount));
    }

    @Override
    public BankAccountEntry updateBankAccount(Long bankAccountId, BankAccountEntry bankAccountEntry) throws EntityNotFoundException {
        BankAccount existingBankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        BankAccount updatedBankAccount = convertToEntity(bankAccountEntry, existingBankAccount);
        return convertToEntry(bankAccountRepository.save(updatedBankAccount));
    }

    @Override
    public void deleteBankAccount(Long bankAccountId) throws EntityNotFoundException {
        bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        bankAccountRepository.deleteById(bankAccountId);
    }

    @Override
    public BankAccountEntry getBankAccountById(Long bankAccountId) throws EntityNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        return convertToEntry(bankAccount);
    }

    private BankAccountEntry convertToEntry(BankAccount bankAccount) {

        BankAccountEntry bankAccountEntry = new BankAccountEntry();
        bankAccountEntry.setBankAccountId(bankAccount.getId());
        bankAccountEntry.setBankName(bankAccount.getBankName());
        bankAccountEntry.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountEntry.setBranchName(bankAccount.getBranchName());
        bankAccountEntry.setIfscCode(bankAccount.getIfscCode());
        bankAccountEntry.setUpiId(bankAccount.getUpiId());
        if(Objects.nonNull(bankAccount.getInstructorId())) {
            bankAccountEntry.setInstructorId(bankAccount.getInstructorId());
        }

        return bankAccountEntry;
    }

    private BankAccount convertToEntity(BankAccountEntry bankAccountEntry, BankAccount existingBankAccount) throws EntityNotFoundException {
        BankAccount bankAccount = (existingBankAccount != null) ? existingBankAccount : new BankAccount();

        if (Objects.nonNull(bankAccountEntry.getBankAccountId())) {
            bankAccount.setId(bankAccountEntry.getBankAccountId());
        }
        if (Objects.nonNull(bankAccountEntry.getAccountNumber())) {
            bankAccount.setAccountNumber(bankAccountEntry.getAccountNumber());
        }
        if (Objects.nonNull(bankAccountEntry.getBankName())) {
            bankAccount.setBankName(bankAccountEntry.getBankName());
        }
        if (Objects.nonNull(bankAccountEntry.getIfscCode())) {
            bankAccount.setIfscCode(bankAccountEntry.getIfscCode());
        }
        if (Objects.nonNull(bankAccountEntry.getUpiId())) {
            bankAccount.setUpiId(bankAccountEntry.getUpiId());
        }
        if (Objects.nonNull(bankAccountEntry.getInstructorId())) {
            bankAccount.setInstructorId(bankAccountEntry.getInstructorId());
        }

        return bankAccount;
    }

}
