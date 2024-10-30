package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.UserResponse;

public interface UserService {

    UserResponse registerUser(UserEntry userEntry);

    UserResponse loginUser(String userName, String password);

    UserResponse updateUser(Long userId, UserEntry userEntry);

    UserResponse deleteUser(Long userId);

    UserResponse getUserById(Long userId);

}
