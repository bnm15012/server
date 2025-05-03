package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService extends BaseService<UserEntry, UserResponse, Long> {

    ResponseEntity<UserResponse> registerUser(UserEntry userEntry);

    ResponseEntity<UserResponse> loginUser(String userName, String password);

    ResponseEntity<UserResponse> getUsersBybranchId(Long branchId);

}
