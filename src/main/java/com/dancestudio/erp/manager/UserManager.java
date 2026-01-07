package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.UserEntry;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserManager extends BaseManagerInt<UserEntry, Long> {

    @Transactional
    UserEntry registerUser(UserEntry userEntry) throws Exception;

    UserEntry loginUser(String userName, String password) throws Exception;

    UserEntry getUserByEmail(String email) throws Exception;

    List<UserEntry> getUserByBranchId(Long branchId) throws Exception;

}
