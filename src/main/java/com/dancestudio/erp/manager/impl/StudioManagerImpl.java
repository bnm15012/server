package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntity;
import static com.dancestudio.erp.util.ConvertToEntryUtil.convertToEntry;

@Service
public class StudioManagerImpl implements StudioManager {

    private final StudioRepository studioRepository;

    @Autowired
    private StudentManagerImpl studentManager;

    @Autowired
    public StudioManagerImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Override
    public StudioEntry addStudio(StudioEntry studioEntry) {
        Studio studio = convertToEntity(studioEntry, null);
        return convertToEntry(studioRepository.save(studio));
    }

    @Override
    public StudioEntry updateStudio(Long studioId, StudioEntry studioEntry) throws EntityNotFoundException {
        Studio existingStudio = studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found"));

        Studio updatedStudio = convertToEntity(studioEntry, existingStudio);
        return convertToEntry(studioRepository.save(updatedStudio));
    }

    @Override
    public Boolean deleteStudio(Long studioId) throws EntityNotFoundException {

        studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found"));
        try {
            if (studentManager.checkIfStudentExistsinStudio(studioId)) {
                throw new RuntimeException("Cannot delete studio: it has some dependency.");
            }
            studioRepository.deleteById(studioId);
            return Boolean.TRUE;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete studio: It has some dependency", e);
        }
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
}
