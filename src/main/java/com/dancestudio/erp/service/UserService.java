package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserResponse> registerUser(UserEntry userEntry);

    ResponseEntity<UserResponse> loginUser(String userName, String password);

    ResponseEntity<UserResponse> updateUser(Long userId, UserEntry userEntry);

    ResponseEntity<UserResponse> deleteUser(Long userId);

    ResponseEntity<UserResponse> getUserById(Long userId);

}
