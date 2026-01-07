package com.dancestudio.erp.modules.member.instructor.bankAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseService;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController extends BaseController<BankAccountEntry, Long> {

    @Autowired
    private BankAccountService bankAccountService;

    @Override
    protected BaseService<BankAccountEntry, Long> getService() {
        return bankAccountService;
    }
}
