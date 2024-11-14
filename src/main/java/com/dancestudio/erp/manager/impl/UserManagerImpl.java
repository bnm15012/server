package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.exception.InvalidCredentialsException;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.repository.SubscriptionPlanRepository;
import com.dancestudio.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntity;
import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntry;

@Service
public class UserManagerImpl implements UserManager {

    private final UserRepository userRepository;

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

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

        String token = jwtUtil.generateAuthToken(user.getEmail(), null);
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

        Date membershipEndDate = subscriptionPlanRepository.findMaxEndDateByStudioId(entry.getStudioEntry().getStudioId());

        String token = jwtUtil.generateAuthToken(user.getEmail(), membershipEndDate);
        entry.setToken(token);
        entry.setMembershipEndDate(membershipEndDate);
        return entry;
    }

    @Override
    public UserEntry updateUser(Long studioId, UserEntry userEntry) throws Exception {
        User existingUser = userRepository.findById(studioId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(Objects.nonNull(userEntry.getPassword())) {
            userEntry.setPassword(hashPassword(userEntry.getPassword()));
        }
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

    @Override
    public UserEntry getUserByEmail(String email) throws EntityNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToEntry(user);
    }


    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

}