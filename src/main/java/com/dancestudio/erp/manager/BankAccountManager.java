package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BankAccountEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

public interface BankAccountManager extends BaseManagerInt<BankAccountEntry, Long> {

    BankAccountEntry getByInstructorId(Long instructorId) throws EntityNotFoundException;

}
