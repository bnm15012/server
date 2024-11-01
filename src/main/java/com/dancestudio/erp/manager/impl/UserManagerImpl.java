package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.UserType;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserManagerImpl implements UserManager {

    private final UserRepository userRepository;

    @Autowired
    private StudioManagerImpl studioManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntry registerUser(UserEntry userEntry) throws Exception {
        if (userRepository.findByName(userEntry.getUserName()).isPresent()) {
            throw new Exception("User already exists");
        }

        // Hash the password before saving
        userEntry.setPassword(hashPassword(userEntry.getPassword()));

        User user = convertToEntity(userEntry);
        return convertToEntry(userRepository.save(user));
    }

    @Override
    public UserEntry loginUser(String username, String password) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("UserName not found"));

        // Check the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return convertToEntry(user);
    }

    @Override
    public UserEntry updateUser(Long studioId, UserEntry userEntry) throws Exception {
        User user = userRepository.findById(studioId)
                .orElseThrow(() -> new RuntimeException("Studio not found"));

        userEntry.setPassword(hashPassword(userEntry.getPassword()));
        User newUserEntry = convertToEntity(userEntry);
        return convertToEntry(userRepository.save(newUserEntry));
    }

    @Override
    public Boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return Boolean.TRUE;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete user: it has dependency.", e);
        }
    }

    @Override
    public UserEntry getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToEntry(user);
    }


    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private UserEntry convertToEntry(User user) {

        UserEntry userEntry = new UserEntry();
        userEntry.setUserName(user.getName());
        userEntry.setEmail(user.getEmail());
        userEntry.setPhone(user.getPhone());
        userEntry.setRole(UserType.valueOf(user.getRole()));
        userEntry.setStudioId(user.getStudio().getId());

        return userEntry;
    }

    public User convertToEntity(UserEntry userEntry) throws Exception {
        if (Objects.isNull(userEntry.getRole())) {
            throw new Exception("RoleType is not a enum");
        }

        User user = new User();
        user.setName(userEntry.getUserName());
        user.setPassword(userEntry.getPassword());
        user.setRole(String.valueOf(userEntry.getRole()));
        user.setPhone(userEntry.getPhone());
        user.setEmail(userEntry.getEmail());

        StudioEntry studioEntry = studioManager.getStudioById(userEntry.getStudioId());
        user.setStudio(studioManager.convertToEntity(studioEntry));

        return user;
    }

}