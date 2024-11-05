package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BankAccountEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

public interface BankAccountManager {

    BankAccountEntry addBankAccount(BankAccountEntry bankAccountEntry) throws EntityNotFoundException;

    BankAccountEntry updateBankAccount(Long bankAccountId, BankAccountEntry bankAccountEntry) throws EntityNotFoundException;

    void deleteBankAccount(Long bankAccountId) throws EntityNotFoundException;

    BankAccountEntry getBankAccountById(Long bankAccountId) throws EntityNotFoundException;
}
