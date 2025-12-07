package com.dancestudio.erp.modules.member.instructor.bankAccount;

import com.dancestudio.erp.controller.BaseController;
import com.dancestudio.erp.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController extends BaseController<BankAccountEntry, BankAccountResponse, Long> {

    @Autowired
    private BankAccountService bankAccountService;

    @Override
    protected BaseService<BankAccountEntry, BankAccountResponse, Long> getService() {
        return bankAccountService;
    }

}
