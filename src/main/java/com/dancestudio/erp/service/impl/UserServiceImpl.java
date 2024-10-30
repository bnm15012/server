package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.UserResponse;
import com.dancestudio.erp.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class UserServiceImpl implements UserService {

    private UserManager userManager;

    @Override
    public UserResponse registerUser(UserEntry userEntry) {

        UserResponse response = new UserResponse();
        try {
            UserEntry entry = userManager.registerUser(userEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "User registered successfully", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }

        return response;
    }

    @Override
    public UserResponse loginUser(String userName, String password) {
        UserResponse response = new UserResponse();
        try {
            UserEntry entry = userManager.loginUser(userName, password);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "User logged in successfully", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }

        return response;
    }

    @Override
    public UserResponse updateUser(Long studioId, UserEntry userEntry) {
        UserResponse response = new UserResponse();

        try {
            UserEntry entry = userManager.updateUser(studioId, userEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "User updated Successfully", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
        }
        return response;
    }

    @Override
    public UserResponse deleteUser(Long userId) {
        UserResponse response = new UserResponse();
        try {
            Boolean isDeleted = userManager.deleteUser(userId);
            response.setStatus(new StatusResponse(1, "User removed Successfully", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }
        return response;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        UserResponse response = new UserResponse();

        UserEntry entry = userManager.getUserById(userId);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

}
