package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.UserResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<UserEntry, UserResponse, Long> {

    @Autowired
    private UserService userService;

    @Override
    protected BaseService<UserEntry, UserResponse, Long> getService() {
        return userService;
    }

    @Override
    public ResponseEntity<UserResponse> add(@RequestBody UserEntry userEntry) {
        return userService.registerUser(userEntry);
    }

    public ResponseEntity<UserResponse> register(@RequestBody UserEntry userEntry) {
        return userService.registerUser(userEntry);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserEntry userEntry) {
        return userService.loginUser(userEntry.getUserName(), userEntry.getPassword());
    }

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<UserResponse> getUsersByBranchId(@PathVariable Long branchId) {
        return userService.getUsersBybranchId(branchId);
    }

}
