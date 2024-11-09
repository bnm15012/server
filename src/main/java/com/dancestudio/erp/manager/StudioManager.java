package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface StudioManager {

    StudioEntry addStudio(StudioEntry studioEntry) throws Exception;

    StudioEntry updateStudio(Long studioId, StudioEntry studioEntry) throws EntityNotFoundException;

    Boolean deleteStudio(Long studioId) throws EntityNotFoundException;

    StudioEntry getStudioById(Long studioId) throws EntityNotFoundException;

    List<StudioEntry> getAllStudios();
}
