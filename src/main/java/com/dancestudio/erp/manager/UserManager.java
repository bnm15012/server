package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.UserEntry;

public interface UserManager extends BaseManager<UserEntry, Long> {

    UserEntry registerUser(UserEntry userEntry) throws Exception;

    UserEntry loginUser(String userName, String password) throws Exception;

    UserEntry getUserByEmail(String email) throws Exception;

}
