package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.BankAccountEntry;
import com.dancestudio.erp.response.BankAccountResponse;
import com.dancestudio.erp.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController extends BaseController<BankAccountEntry, BankAccountResponse, Long> {

    @Autowired
    private BankAccountService bankAccountService;

    @Override
    public ResponseEntity<BankAccountResponse> add(@RequestBody BankAccountEntry bankAccountEntry) {
        return bankAccountService.add(bankAccountEntry);
    }

    @Override
    public ResponseEntity<BankAccountResponse> update(@PathVariable Long id, @RequestBody BankAccountEntry bankAccountEntry) {
        return bankAccountService.update(id, bankAccountEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return bankAccountService.delete(id);
    }

    @Override
    public ResponseEntity<BankAccountResponse> get(@PathVariable Long id) {
        return bankAccountService.get(id);
    }
}
