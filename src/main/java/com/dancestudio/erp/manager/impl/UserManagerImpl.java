package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.converter.UserAccessConvertor;
import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.UserType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.exception.InvalidCredentialsException;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.manager.TemplateManager;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.repository.UserRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dancestudio.erp.constants.TemplateName.ADD_NEW_USER_EMAIL;
import static com.dancestudio.erp.constants.TemplateName.UPDATE_USER_EMAIL;
import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntity;
import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntry;

@Service
@Setter(onMethod = @__({@Autowired}))
public class UserManagerImpl implements UserManager {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private NotificationManager notificationManager;
    private TemplateManager templateManager;

    @Autowired
    public UserManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntry registerUser(UserEntry userEntry) throws Exception {
        if (userRepository.findByName(userEntry.getUserName()).isPresent()) {
            throw new Exception("UserName already exists");
        }
        if(Objects.isNull(userEntry.getRole())) {
            userEntry.setRole(UserType.MANAGER);
        }

        if(Objects.isNull(userEntry.getPassword())) {
            String password = PasswordManagerImpl.generateRandomPassword();
            userEntry.setPassword(password);
        }

        // Hash the password before saving
        String password = userEntry.getPassword();
        userEntry.setPassword(hashPassword(password));
        User user = convertToEntity(userEntry, null);
        user.setUserAccess(UserAccessConvertor.defaultAccessLevel(userEntry.getRole() == UserType.ADMIN, user));
        user = userRepository.save(user);
        UserEntry entry = convertToEntry(user);

        String token = jwtUtil.generateAuthToken(user.getEmail(), null);
        entry.setToken(token);
        entry.setPassword(password);
        if(!UserType.ADMIN.equals(entry.getRole())) {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(ADD_NEW_USER_EMAIL);
            String updatedBody = formatEmailBody(user, templateEntry, entry);

            notificationManager.sendEmail(userEntry.getEmail(), templateEntry.getSubject(), updatedBody, null, null, null);
        }

        return entry;
    }

    @Override
    public UserEntry loginUser(String username, String password) throws Exception {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new EntityNotFoundException("UserName not found"));

        if (!user.isEnabled()) {
            throw new EntityNotFoundException("User is not enabled");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        UserEntry entry = convertToEntry(user);

        String token = jwtUtil.generateAuthToken(user.getEmail(), (entry.getSubscriptionEntry() != null) ? entry.getSubscriptionEntry().getEndDate() : null);

        entry.setToken(token);
        return entry;
    }

    @Override
    public UserEntry add(UserEntry userEntry) throws Exception {
        if (userRepository.findByName(userEntry.getUserName()).isPresent()) {
            throw new IllegalArgumentException("UserName already exists");
        }

        User user = ConvertToEntryUtil.convertToEntity(userEntry, null);
        return ConvertToEntryUtil.convertToEntry(userRepository.save(user));
    }

    @Override
    public UserEntry update(Long studioId, UserEntry userEntry) throws Exception {
        User existingUser = userRepository.findById(studioId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Objects.nonNull(userEntry.getPassword())) {
            userEntry.setPassword(hashPassword(userEntry.getPassword()));
        }
        User updatedUser = convertToEntity(userEntry, existingUser);
        UserEntry entry = convertToEntry(userRepository.save(updatedUser));

        TemplateEntry templateEntry = templateManager.getTemplateDetails(UPDATE_USER_EMAIL);
        String updatedBody = templateEntry.getTemplateBody().replace("{user_name}", existingUser.getName());

        notificationManager.sendEmail(entry.getEmail(), templateEntry.getSubject(), updatedBody, null, null, null);
        return entry;
    }

    @Override
    public void delete(Long userId) throws EntityNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        try {
            userRepository.deleteById(userId);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete user, It has some dependency ", e);
        }
    }

    @Override
    public UserEntry getById(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToEntry(user);
    }

    @Override
    public UserEntry getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToEntry(user);
    }

    @SneakyThrows
    @Override
    public List<UserEntry> getUserByBranchId(Long branchId) {
        List<User> userList = userRepository.findByBranchId(branchId);
        List<UserEntry> userEntries = new ArrayList<>();
        for (User user : userList) {
            if (!Objects.equals(user.getRole(), UserType.ADMIN.name())) {
                userEntries.add(ConvertToEntryUtil.convertToEntry(user));
            }
        }
        return userEntries;
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String formatEmailBody(User user, TemplateEntry templateEntry, UserEntry userEntry) {
        String updatedBody = templateEntry.getTemplateBody()
                .replace("{user_name}", user.getName())
                .replace("{username}", userEntry.getUserName())
                .replace("{password}", userEntry.getPassword());
        return updatedBody;
    }


}