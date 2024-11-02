package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.exception.InvalidCredentialsException;

public interface UserManager {

    UserEntry registerUser(UserEntry userEntry) throws Exception;

    UserEntry loginUser(String userName, String password) throws EntityNotFoundException, InvalidCredentialsException;

    UserEntry updateUser(Long userId, UserEntry userEntry) throws Exception;

    Boolean deleteUser(Long userId);

    UserEntry getUserById(Long userId);

}
