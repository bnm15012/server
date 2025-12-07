package com.dancestudio.erp.modules.member.instructor.bankAccount;


import com.dancestudio.erp.base.BaseService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter(onMethod = @__({@Autowired}))
@Component
public class BankAccountService extends BaseService<BankAccountEntry, Long>{

    private BankAccountManager bankAccountManager;

    @Override
    protected BankAccountEntry doAdd(BankAccountEntry entry) throws Exception {
        return bankAccountManager.add(entry);
    }

    @Override
    protected BankAccountEntry doUpdate(Long id, BankAccountEntry entry) throws Exception {
        return bankAccountManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        bankAccountManager.delete(id);
    }

    @Override
    protected BankAccountEntry doGet(Long id) throws Exception {
        return bankAccountManager.getById(id);
    }

}
