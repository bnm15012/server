package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.response.StudioResponse;
import org.springframework.http.ResponseEntity;

public interface StudioService extends BaseService<StudioEntry, StudioResponse, Long> {

    ResponseEntity<StudioResponse> getAllStudios();
}
