package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.BankAccountEntry;
import com.dancestudio.erp.response.BankAccountResponse;
import com.dancestudio.erp.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/add")
    public ResponseEntity<BankAccountResponse> addBankAccount(@RequestBody BankAccountEntry bankAccountEntry) {
        return bankAccountService.addBankAccount(bankAccountEntry);
    }

    @PutMapping("/update/{bankAccountId}")
    public ResponseEntity<BankAccountResponse> updateBankAccount(@PathVariable Long bankAccountId, @RequestBody BankAccountEntry bankAccountEntry) {
        return bankAccountService.updateBankAccount(bankAccountId, bankAccountEntry);
    }

    @DeleteMapping("/delete/{bankAccountId}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long bankAccountId) {
        return bankAccountService.deleteBankAccount(bankAccountId);
    }

    @GetMapping("/get/{bankAccountId}")
    public ResponseEntity<BankAccountResponse> getBankAccountById(@PathVariable Long bankAccountId) {
        return bankAccountService.getBankAccountById(bankAccountId);
    }
}
