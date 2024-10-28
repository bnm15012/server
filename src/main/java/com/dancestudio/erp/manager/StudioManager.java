package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudioEntry;

import java.util.List;

public interface StudioManager {
    StudioEntry addStudio(StudioEntry studioEntry);

    StudioEntry updateStudio(Long studioId, StudioEntry studioEntry);

    void deleteStudio(Long studioId);

    StudioEntry getStudioById(Long studioId);

    List<StudioEntry> getAllStudios();
}
