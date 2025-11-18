package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.entry.activity.ActivityMembershipTypeEntry;
import com.dancestudio.erp.response.ActivityMembershipTypeResponse;

public interface ActivityMembershipTypeService
        extends BaseService<ActivityMembershipTypeEntry, ActivityMembershipTypeResponse, Long> {

    public ResponseEntity<ActivityMembershipTypeResponse> getAllByStudioId(Long stuidId, Integer page, Integer size);

}
