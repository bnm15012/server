package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.GenricTemplateEntry;
import com.dancestudio.erp.response.GenericTemplateResponse;
import org.springframework.http.ResponseEntity;

public interface GenricTemplateService extends BaseService<GenricTemplateEntry, GenericTemplateResponse, Long> {

    ResponseEntity<GenericTemplateResponse> getAllByStudioId(Long studioId, String templateType, int page, int size);
}
