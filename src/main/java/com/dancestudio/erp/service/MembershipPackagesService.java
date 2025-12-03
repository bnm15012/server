package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.entry.activity.MembershipPackagesEntry;
import com.dancestudio.erp.response.MembershipPackagesResponse;

public interface MembershipPackagesService
        extends BaseService<MembershipPackagesEntry, MembershipPackagesResponse, Long> {

    public ResponseEntity<MembershipPackagesResponse> getAllByStudioId(Long stuidId, Integer page, Integer size);

}
