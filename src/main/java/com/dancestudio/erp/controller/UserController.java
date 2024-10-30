package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.UserResponse;
import com.dancestudio.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserEntry userEntry) {
        return userService.registerUser(userEntry);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserEntry userEntry) {
        return userService.loginUser(userEntry.getUserName(), userEntry.getPassword());
    }

    @PutMapping("/update/{userId}")
    public UserResponse updateUser(@PathVariable Long userId, @RequestBody UserEntry userEntry) {
        return userService.updateUser(userId, userEntry);
    }

    @DeleteMapping("/delete/{userId}")
    public UserResponse deleteSUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/get/{userId}")
    public UserResponse getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

}
