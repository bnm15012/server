package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.response.MembershipFeeResponse;
import com.dancestudio.erp.service.MembershipFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membership")
public class MembershipFeeController {

    @Autowired
    private MembershipFeeService membershipFeeService;

    @PostMapping("/create")
    public ResponseEntity<MembershipFeeResponse> createMembershipFee(@RequestBody MembershipFeeEntry membershipFeeEntry) {
        return membershipFeeService.addMembershipFee(membershipFeeEntry);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MembershipFeeResponse> getMembershipFee(@PathVariable Long id) {
        return membershipFeeService.getMembershipFee(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MembershipFeeResponse> updateMembershipFee(@PathVariable Long id, @RequestParam Double newFeeAmount) {
        return membershipFeeService.updateMembershipFee(id, newFeeAmount);
    }

    @GetMapping("/getFeesByStudio/{studioId}")
    public ResponseEntity<MembershipFeeResponse> getFeesByStudio(@PathVariable Long studioId) {
        return membershipFeeService.getMembershipFeesByStudio(studioId);
    }

}
