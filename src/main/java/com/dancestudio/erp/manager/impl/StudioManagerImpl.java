package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.ConfigurationType;
import com.dancestudio.erp.enums.UserType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.dancestudio.erp.constants.TemplateName.ADD_NEW_STUDIO_EMAIL;
import static com.dancestudio.erp.constants.TemplateName.UPDATE_STUDIO_EMAIL;
import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntity;
import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntry;

@Service
public class StudioManagerImpl implements StudioManager {

    private final StudioRepository studioRepository;

    @Autowired private UserManager userManager;
    @Autowired private NotificationManager notificationManager;
    @Autowired private TemplateManager templateManager;
    @Autowired private BranchManager branchManager;

    @Autowired
    public StudioManagerImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public StudioEntry add(StudioEntry studioEntry) throws Exception {
        if(studioRepository.findByName(studioEntry.getStudioName()).isPresent()) {
            throw new EntityNotFoundException("Studio already exists");
        }

        addDefaultConfiguration(studioEntry);
        Studio studio = convertToEntity(studioEntry, null);
        studio = studioRepository.save(studio);

        BranchEntry branchEntry = studioEntry.getBranchList().get(0);
        if(Objects.nonNull(branchEntry)) {
            branchEntry.setName("MAIN_BRANCH");
            branchEntry.setStudioId(studio.getId());
            branchEntry = branchManager.add(branchEntry);
        }

        StudioEntry updateStudioEntry = convertToEntry(studio);
        updateStudioEntry.setBranchList(Collections.singletonList(branchEntry));
        autoRegisterNewUser(studioEntry, studio);
        return updateStudioEntry;
    }

    @Override
    public StudioEntry update(Long studioId, StudioEntry studioEntry) throws Exception {
        Studio existingStudio = studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found"));

        Studio updatedStudio = convertToEntity(studioEntry, existingStudio);
        updatedStudio = studioRepository.save(updatedStudio);

        TemplateEntry templateEntry = templateManager.getTemplateDetails(UPDATE_STUDIO_EMAIL);
        templateEntry.setTemplateBody(templateEntry.getTemplateBody()
                .replace("{studio_name}", updatedStudio.getName()));

        if(Objects.nonNull(studioEntry.getEmail())) {
            notificationManager.sendEmail(studioEntry.getEmail(), templateEntry.getSubject(), templateEntry.getTemplateBody(), null);
        }
        return convertToEntry(updatedStudio);
    }

    @Override
    public void delete(Long studioId) throws EntityNotFoundException {
        studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found"));

        studioRepository.deleteById(studioId);
    }

    @Override
    public StudioEntry getById(Long studioId) throws Exception {
        Studio studio = studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found"));

        return convertToEntry(studio);
    }

    @Override
    public List<StudioEntry> getAllStudios() throws Exception {
        List<Studio> entries = studioRepository.findAll().stream().toList();

        List<StudioEntry> studioEntries = new ArrayList<>();
        for (Studio entry : entries) {
            StudioEntry studioEntry = convertToEntry(entry);
            studioEntries.add(studioEntry);
        }

        return studioEntries;
    }

    private void autoRegisterNewUser(StudioEntry studioEntry, Studio studio) throws Exception {
        UserEntry userEntry = new UserEntry();
        userEntry.setUserName(studioEntry.getUserName());

        String password = PasswordManagerImpl.generateRandomPassword();
        userEntry.setPassword(password);

        userEntry.setPhone(studioEntry.getContactDetails());
        userEntry.setEmail(studioEntry.getEmail());
        userEntry.setRole(UserType.ADMIN);
        userEntry.setStudioEntry(convertToEntry(studio));
        userManager.registerUser(userEntry);
        userEntry.setPassword(password);

        TemplateEntry templateEntry = templateManager.getTemplateDetails(ADD_NEW_STUDIO_EMAIL);
        String updatedBody = formatEmailBody(studio, templateEntry, userEntry);

        notificationManager.sendEmail(userEntry.getEmail(), templateEntry.getSubject(), updatedBody, null);
    }

    private String formatEmailBody(Studio studio, TemplateEntry templateEntry, UserEntry userEntry) {
        String updatedBody = templateEntry.getTemplateBody()
                .replace("{studio_name}", studio.getName())
                .replace("{username}", userEntry.getUserName())
                .replace("{password}", userEntry.getPassword());
        return updatedBody;
    }

    private void addDefaultConfiguration(StudioEntry studioEntry) {
        List<StudioConfigurationEntry> configurationEntryList = new ArrayList<>();
        for (ConfigurationType configurationType : ConfigurationType.values()) {
            StudioConfigurationEntry configEntry = new StudioConfigurationEntry();
            configEntry.setNavBarName(configurationType.name());
            configEntry.setEnabled(true);
            configurationEntryList.add(configEntry);
        }

        StudioConfigurationRequest configurationRequest = new StudioConfigurationRequest();
        configurationRequest.setConfigrationEntryList(configurationEntryList);
        studioEntry.setConfiguration(configurationRequest);
    }

}
