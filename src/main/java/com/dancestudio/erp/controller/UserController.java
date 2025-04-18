package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.UserResponse;
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
    public ResponseEntity<UserResponse> add(@RequestBody UserEntry userEntry) {
        return userService.registerUser(userEntry);
    }

    @Override
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserEntry userEntry) {
        return userService.update(id, userEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @Override
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserEntry userEntry) {
        return userService.registerUser(userEntry);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserEntry userEntry) {
        return userService.loginUser(userEntry.getUserName(), userEntry.getPassword());
    }
}
