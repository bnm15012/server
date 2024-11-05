package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.BankAccountEntry;
import com.dancestudio.erp.response.BankAccountResponse;
import org.springframework.http.ResponseEntity;

public interface BankAccountService {

    ResponseEntity<BankAccountResponse> addBankAccount(BankAccountEntry bankAccountEntry);

    ResponseEntity<BankAccountResponse> updateBankAccount(Long bankAccountId, BankAccountEntry bankAccountEntry);

    ResponseEntity<Void> deleteBankAccount(Long bankAccountId);

    ResponseEntity<BankAccountResponse> getBankAccountById(Long bankAccountId);
}
