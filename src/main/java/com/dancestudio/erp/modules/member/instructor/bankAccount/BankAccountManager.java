package com.dancestudio.erp.modules.member.instructor.bankAccount;

import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BaseManagerInt;

public interface BankAccountManager extends BaseManagerInt<BankAccountEntry, Long> {

    BankAccountEntry getByInstructorId(Long instructorId) throws EntityNotFoundException;

}
