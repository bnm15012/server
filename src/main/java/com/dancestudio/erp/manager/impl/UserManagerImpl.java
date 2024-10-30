package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static org.bouncycastle.crypto.generators.OpenBSDBCrypt.checkPassword;

@Service
public class UserManagerImpl implements UserManager {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntry registerUser(UserEntry userEntry) {
        // Hash the password before saving
        userEntry.setPassword(hashPassword(userEntry.getPassword()));

        User user = convertToEntity(userEntry);
        return convertToEntry(userRepository.save(user));
    }

    @Override
    public UserEntry loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("UserName not found"));

        // Check the password
        if (!checkPassword(password, user.getPassword().toCharArray())) {
            throw new RuntimeException("Invalid credentials");
        }
        return convertToEntry(user);
    }

    @Override
    public UserEntry updateUser(Long studioId, UserEntry userEntry) {
        User user = userRepository.findById(studioId)
                .orElseThrow(() -> new RuntimeException("Studio not found"));

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
        userEntry.setPassword(user.getName());
        userEntry.setEmail(user.getEmail());
        userEntry.setPhone(user.getPhone());
        user.setRole(user.getRole());

        return userEntry;
    }

    public User convertToEntity(UserEntry userEntry) {

        User user = new User();
        user.setName(userEntry.getUserName());
        user.setPassword(userEntry.getPassword());
        user.setRole(userEntry.getRole());
        user.setPhone(userEntry.getPhone());
        user.setEmail(userEntry.getEmail());

        return user;
    }

}