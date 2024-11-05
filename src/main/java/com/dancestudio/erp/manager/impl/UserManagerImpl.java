package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.UserType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.exception.InvalidCredentialsException;
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
    private StudioManagerImpl studioManagerImpl;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public UserManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntry registerUser(UserEntry userEntry) throws Exception {
        if (userRepository.findByName(userEntry.getUserName()).isPresent()) {
            throw new Exception("UserName already exists");
        }

        // Hash the password before saving
        userEntry.setPassword(hashPassword(userEntry.getPassword()));
        User user = convertToEntity(userEntry, null);
        user = userRepository.save(user);
        UserEntry entry = convertToEntry(user);

        String token = jwtUtil.generateAuthToken(user.getEmail());
        entry.setToken(token);

        return entry;
    }

    @Override
    public UserEntry loginUser(String username, String password) throws EntityNotFoundException, InvalidCredentialsException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new EntityNotFoundException("UserName not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        UserEntry entry = convertToEntry(user);

        String token = jwtUtil.generateAuthToken(user.getEmail());
        entry.setToken(token);

        return entry;
    }

    @Override
    public UserEntry updateUser(Long studioId, UserEntry userEntry) throws Exception {
        User existingUser = userRepository.findById(studioId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User updatedUser = convertToEntity(userEntry, existingUser);
        return convertToEntry(userRepository.save(updatedUser));
    }

    @Override
    public Boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return Boolean.TRUE;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete user, It has some dependency ", e);
        }
    }

    @Override
    public UserEntry getUserById(Long userId) throws EntityNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToEntry(user);
    }


    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private UserEntry convertToEntry(User user) throws EntityNotFoundException {

        UserEntry userEntry = new UserEntry();
        userEntry.setUserId(user.getId());
        userEntry.setUserName(user.getName());
        userEntry.setEmail(user.getEmail());
        userEntry.setPhone(user.getPhone());
        userEntry.setRole(UserType.valueOf(user.getRole()));

        if(Objects.nonNull(user.getStudioId())) {
            StudioEntry studioEntry = studioManagerImpl.getStudioById(user.getStudioId());
            userEntry.setStudioEntry(studioEntry);
        }
        return userEntry;
    }

    public User convertToEntity(UserEntry userEntry, User existingUser) throws Exception {
        User user = (existingUser != null) ? existingUser : new User();
        user.setId(null);

        if (Objects.nonNull(userEntry.getUserId())) {
            user.setId(userEntry.getUserId());
        }
        if (Objects.nonNull(userEntry.getUserName())) {
            user.setName(userEntry.getUserName());
        }
        if (Objects.nonNull(userEntry.getPassword())) {
            user.setPassword(userEntry.getPassword());
        }
        if (Objects.nonNull(userEntry.getRole())) {
            user.setRole(String.valueOf(userEntry.getRole()));
        }
        if (Objects.nonNull(userEntry.getPhone())) {
            user.setPhone(userEntry.getPhone());
        }
        if (Objects.nonNull(userEntry.getEmail())) {
            user.setEmail(userEntry.getEmail());
        }

        if (Objects.nonNull(userEntry.getStudioEntry()) && Objects.nonNull(userEntry.getStudioEntry().getStudioId())) {
            Long studioId = userEntry.getStudioEntry().getStudioId();

            StudioEntry studioEntry = studioManagerImpl.getStudioById(studioId);
            user.setStudioId(studioEntry.getStudioId());
        }

        return user;
    }

}