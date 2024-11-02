package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.response.StudioResponse;
import org.springframework.http.ResponseEntity;

public interface StudioService {
    ResponseEntity<StudioResponse> addStudio(StudioEntry studioEntry);

    ResponseEntity<StudioResponse> updateStudio(Long studioId, StudioEntry studioEntry);

    ResponseEntity<Void> deleteStudio(Long studioId);

    ResponseEntity<StudioResponse> getStudioById(Long studioId);

    ResponseEntity<StudioResponse> getAllStudios();
}
