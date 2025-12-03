package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.activity.MembershipPackagesEntry;
import com.dancestudio.erp.response.MembershipPackagesResponse;
import com.dancestudio.erp.service.MembershipPackagesService;
import com.dancestudio.erp.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membershipPackages")
public class MembershipPackagesController extends BaseController<MembershipPackagesEntry, MembershipPackagesResponse, Long> {

    @Autowired
    private MembershipPackagesService membershipPackagesService;

    @Override
    protected BaseService<MembershipPackagesEntry, MembershipPackagesResponse, Long> getService() {
        return membershipPackagesService;
    }

    @GetMapping("/getAll/{studioId}")
    public ResponseEntity<MembershipPackagesResponse> getAll(@PathVariable Long studioId, 
        @RequestParam(defaultValue = "1") Integer page, 
        @RequestParam(defaultValue = "10") Integer size) {
        return membershipPackagesService.getAllByStudioId(studioId, page, size);
    }
}
