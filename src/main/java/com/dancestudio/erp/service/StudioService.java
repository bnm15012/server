package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.response.StudioResponse;

public interface StudioService {
    StudioResponse addStudio(StudioEntry studioEntry);

    StudioResponse updateStudio(Long studioId, StudioEntry studioEntry);

    void deleteStudio(Long studioId);

    StudioResponse getStudioById(Long studioId);

    StudioResponse getAllStudios();
}
