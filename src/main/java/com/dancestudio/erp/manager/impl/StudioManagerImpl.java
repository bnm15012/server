package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.repository.StudioRepository;
import com.dancestudio.erp.manager.StudioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudioManagerImpl implements StudioManager {
    private final StudioRepository studioRepository;

    @Autowired
    public StudioManagerImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Override
    public StudioEntry addStudio(StudioEntry studioEntry) {
        Studio studio = convertToEntity(studioEntry);
        return convertToEntry(studioRepository.save(studio));
    }

    @Override
    public StudioEntry updateStudio(Long studioId, StudioEntry studioEntry) {
        Studio studio = studioRepository.findById(studioId)
                .orElseThrow(() -> new RuntimeException("Studio not found"));

        Studio newStudioEntry = convertToEntity(studioEntry);
        return convertToEntry(studioRepository.save(newStudioEntry));
    }

    @Override
    public void deleteStudio(Long studioId) {
        studioRepository.deleteById(studioId);
    }

    @Override
    public StudioEntry getStudioById(Long studioId) {
        Studio studio = studioRepository.findById(studioId)
                .orElseThrow(() -> new RuntimeException("Studio not found"));

        return convertToEntry(studio);
    }

    @Override
    public List<StudioEntry> getAllStudios() {
        List<Studio> entries = studioRepository.findAll().stream().collect(Collectors.toList());

        List<StudioEntry> studioEntries = new ArrayList<>();
        for (Studio entry : entries) {
            StudioEntry studioEntry = convertToEntry(entry);
            studioEntries.add(studioEntry);
        }

        return studioEntries;
    }

    private StudioEntry convertToEntry(Studio studio) {

        StudioEntry studioEntry = new StudioEntry();
        studioEntry.setStudioId(studio.getId());
        studioEntry.setStudioName(studio.getStudioName());
        studioEntry.setLocation(studio.getLocation());
        studioEntry.setContactDetails(studio.getContactDetails());

        return studioEntry;
    }

    public Studio convertToEntity(StudioEntry studioEntry) {

        Studio studio = new Studio();
        studio.setId(studioEntry.getStudioId());
        studio.setStudioName(studioEntry.getStudioName());
        studio.setLocation(studioEntry.getLocation());
        studio.setContactDetails(studioEntry.getContactDetails());

        return studio;
    }
}
