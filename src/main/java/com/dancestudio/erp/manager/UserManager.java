package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.UserEntry;

public interface UserManager {

    UserEntry registerUser(UserEntry userEntry);

    UserEntry loginUser(String userName, String password);

    UserEntry updateUser(Long userId, UserEntry userEntry);

    Boolean deleteUser(Long userId);

    UserEntry getUserById(Long userId);

}
