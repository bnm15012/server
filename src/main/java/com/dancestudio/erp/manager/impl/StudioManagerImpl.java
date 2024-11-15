package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.UserType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EmailManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.manager.TemplateManager;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.dancestudio.erp.constants.TemplateName.ADD_NEW_STUDIO_EMAIL;
import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntity;
import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntry;

@Service
public class StudioManagerImpl implements StudioManager {

    private final StudioRepository studioRepository;

    @Autowired
    private UserManager userManager;

    @Autowired
    private EmailManager emailManager;

    @Autowired
    private TemplateManager templateManager;

    @Autowired
    public StudioManagerImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public StudioEntry addStudio(StudioEntry studioEntry) throws Exception {
        if(studioRepository.findByName(studioEntry.getStudioName()).isPresent()) {
            throw new EntityNotFoundException("Studio already exists");
        }

        Studio studio = convertToEntity(studioEntry, null);
        studio = studioRepository.save(studio);

        autoRegisterNewUser(studioEntry, studio);
        return convertToEntry(studio);
    }

    @Override
    public StudioEntry updateStudio(Long studioId, StudioEntry studioEntry) throws EntityNotFoundException {
        Studio existingStudio = studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found"));

        Studio updatedStudio = convertToEntity(studioEntry, existingStudio);
        updatedStudio = studioRepository.save(updatedStudio);

//        TemplateEntry templateEntry = templateManager.getTemplateDetails(UPDATE_STUDIO_EMAIL);
//        templateEntry.setTemplateBody(templateEntry.getTemplateBody()
//                .replace("{studio_name}", updatedStudio.getName()));
//
//        emailManager.sendEmail(studioEntry.getEmail(), templateEntry.getSubject(), templateEntry.getTemplateBody());
        return convertToEntry(updatedStudio);
    }

    @Override
    public Boolean deleteStudio(Long studioId) throws EntityNotFoundException {
        studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found"));

        studioRepository.deleteById(studioId);
        return Boolean.TRUE;
    }

    @Override
    public StudioEntry getStudioById(Long studioId) throws EntityNotFoundException {
        Studio studio = studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found"));

        return convertToEntry(studio);
    }

    @Override
    public List<StudioEntry> getAllStudios() {
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
        userEntry.setRole(UserType.MANAGER);
        userEntry.setStudioEntry(convertToEntry(studio));

        userManager.registerUser(userEntry);
        userEntry.setPassword(password);

        TemplateEntry templateEntry = templateManager.getTemplateDetails(ADD_NEW_STUDIO_EMAIL);
        String updatedBody = formatEmailBody(studio, templateEntry, userEntry);

        emailManager.sendEmail(userEntry.getEmail(), templateEntry.getSubject(), updatedBody);
    }

    private String formatEmailBody(Studio studio, TemplateEntry templateEntry, UserEntry userEntry) {
        String updatedBody = templateEntry.getTemplateBody()
                .replace("{studio_name}", studio.getName())
                .replace("{username}", userEntry.getUserName())
                .replace("{password}", userEntry.getPassword());
        return updatedBody;
    }
}
