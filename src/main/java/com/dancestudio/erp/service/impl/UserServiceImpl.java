package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.exception.InvalidCredentialsException;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.UserResponse;
import com.dancestudio.erp.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class UserServiceImpl implements UserService {

    private UserManager userManager;

    @Override
    public ResponseEntity<UserResponse> registerUser(UserEntry userEntry) {

        UserResponse response = new UserResponse();
        try {
            UserEntry entry = userManager.registerUser(userEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "User registered successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<UserResponse> loginUser(String userName, String password) {
        UserResponse response = new UserResponse();
        try {
            UserEntry entry = userManager.loginUser(userName, password);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "User logged in successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            response.setStatus(new StatusResponse(1, "User not found", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (InvalidCredentialsException ex) {
            response.setStatus(new StatusResponse(1, "Invalid credentials", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long studioId, UserEntry userEntry) {
        UserResponse response = new UserResponse();

        try {
            UserEntry entry = userManager.updateUser(studioId, userEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "User updated successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            response.setStatus(new StatusResponse(0, "User or Studio not found", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<UserResponse> deleteUser(Long userId) {
        UserResponse response = new UserResponse();
        try {
            Boolean isDeleted = userManager.deleteUser(userId);
            if (isDeleted) {
                response.setStatus(new StatusResponse(1, "User removed successfully", StatusResponse.Type.SUCCESS));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            } else {
                response.setStatus(new StatusResponse(0, "User not found", StatusResponse.Type.ERROR));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long userId) {
        UserResponse response = new UserResponse();

        try {
            UserEntry entry = userManager.getUserById(userId);
            if (entry != null) {
                response.setData(Collections.singletonList(entry));
                response.setStatus(new StatusResponse(1, "User fetched successfully", StatusResponse.Type.SUCCESS));
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(new StatusResponse(0, "User not found", StatusResponse.Type.ERROR));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
