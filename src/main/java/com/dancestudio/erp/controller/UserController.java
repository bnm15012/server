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

    @PutMapping("/update/{studioId}")
    public UserResponse updateStudio(@PathVariable Long studioId, @RequestBody UserEntry userEntry) {
        return userService.updateUser(studioId, userEntry);
    }

    @DeleteMapping("/delete/{studioId}")
    public UserResponse deleteStudio(@PathVariable Long studioId) {
        return userService.deleteUser(studioId);
    }

    @GetMapping("/get/{studioId}")
    public UserResponse getStudioById(@PathVariable Long studioId) {
        return userService.getUserById(studioId);
    }

}
