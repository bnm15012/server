package com.dancestudio.erp.modules.studio;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudioConfigurationRequest;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.manager.impl.BranchManagerImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class StudioConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static StudioEntry convertToEntry(Studio studio) throws Exception {

        StudioEntry studioEntry = new StudioEntry();
        studioEntry.setStudioId(studio.getId());
        studioEntry.setStudioName(studio.getName());
        studioEntry.setLocation(studio.getLocation());
        studioEntry.setLogo(studio.getLogo());
        studioEntry.setEmail(studio.getEmail());
        studioEntry.setGstNumber(studio.getGstNumber());
        studioEntry.setPasscode(studio.getPasscode());
        studioEntry.setContactDetails(studio.getContactDetails());
        studioEntry.setAmcEnabled(studio.getAmcEnabled());

        if (studio.getConfiguration() != null) {
            try {
                Map<String, Boolean> configurationMap = objectMapper.readValue(
                        studio.getConfiguration(),
                        new TypeReference<Map<String, Boolean>>() {
                        });

                StudioConfigurationRequest request = new StudioConfigurationRequest();
                request.setConfigrationEntryList(configurationMap);
                studioEntry.setConfiguration(request);
            } catch (Exception e) {
                // throw new RuntimeException("Error parsing configuration settings JSON ", e);
            }
        }

        try {
            BranchManagerImpl branchManager = applicationContext.getBean(BranchManagerImpl.class);
            List<BranchEntry> branchEntries = branchManager.findByStudioId(studio.getId());
            studioEntry.setBranchList(branchEntries);
        } catch (Exception ex) {
            studioEntry.setBranchList(null);
        }

        return studioEntry;
    }

    public static Studio convertToEntity(StudioEntry studioEntry, Studio existingStudio) {
        Studio studio = (existingStudio != null) ? existingStudio : new Studio();

        if (Objects.nonNull(studioEntry.getStudioId())) {
            studio.setId(studioEntry.getStudioId());
        }
        if (Objects.nonNull(studioEntry.getStudioName())) {
            studio.setName(studioEntry.getStudioName());
        }
        if (Objects.nonNull(studioEntry.getLogo())) {
            studio.setLogo(studioEntry.getLogo());
        }
        if (Objects.nonNull(studioEntry.getGstNumber())) {
            studio.setGstNumber(studioEntry.getGstNumber());
        }
        if (Objects.nonNull(studioEntry.getEmail())) {
            studio.setEmail(studioEntry.getEmail());
        }
        if (Objects.nonNull(studioEntry.getPasscode())) {
            studio.setPasscode(studioEntry.getPasscode());
        }
        if (Objects.nonNull(studioEntry.getLocation())) {
            studio.setLocation(studioEntry.getLocation());
        }
        if (Objects.nonNull(studioEntry.getContactDetails())) {
            studio.setContactDetails(studioEntry.getContactDetails());
        }
        if (Objects.nonNull(studioEntry.getConfiguration())) {
            Map<String, Boolean> configurationMap = studioEntry.getConfiguration()
                    .getConfigrationEntryList();
            try {
                String configurationJson = objectMapper.writeValueAsString(configurationMap);
                studio.setConfiguration(configurationJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting configuration settings to JSON", e);
            }
        }
        if (Objects.nonNull(studioEntry.getAmcEnabled())) {
            studio.setAmcEnabled(studioEntry.getAmcEnabled());
        }

        return studio;
    }

    public static StudioEntry convertToEntry(Studio studio, String[] fields) throws Exception {
        StudioEntry studioEntry = new StudioEntry();
        if (fields == null) {
            fields = new String[] { }; // empty array
        }
        studioEntry.setStudioId(studio.getId());

        if(Arrays.asList(fields).contains("studioName")){
            studioEntry.setStudioName(studio.getName());
        }
        if(Arrays.asList(fields).contains("location")){
            studioEntry.setLocation(studio.getLocation());
        }
        if(Arrays.asList(fields).contains("logo")){
            studioEntry.setLogo(studio.getLogo());
        }
        if(Arrays.asList(fields).contains("email")){
            studioEntry.setEmail(studio.getEmail());
        }
        if(Arrays.asList(fields).contains("gstNumber")){
            studioEntry.setGstNumber(studio.getGstNumber());
        }
        if(Arrays.asList(fields).contains("passcode")){
            studioEntry.setPasscode(studio.getPasscode());
        }
        if(Arrays.asList(fields).contains("contactDetails")){
            studioEntry.setContactDetails(studio.getContactDetails());
        }
        return studioEntry;
    }

}
