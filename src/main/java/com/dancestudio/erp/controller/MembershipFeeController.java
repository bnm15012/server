package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.response.MembershipFeeResponse;
import com.dancestudio.erp.service.MembershipFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class MembershipFeeController {

    @Autowired
    private MembershipFeeService membershipFeeService;

    @PostMapping
    public MembershipFeeResponse createMembershipFee(@RequestBody MembershipFeeEntry membershipFeeEntry) {
        return membershipFeeService.addMembershipFee(membershipFeeEntry);
    }

    @PutMapping("/{id}")
    public MembershipFeeResponse updateMembershipFee(@PathVariable Long id, @RequestParam Double newFeeAmount) {
        return membershipFeeService.updateMembershipFee(id, newFeeAmount);
    }

    @GetMapping("/{studioId}")
    public MembershipFeeResponse getFeesByStudio(@PathVariable Long studioId) {
        return membershipFeeService.getMembershipFeesByStudio(studioId);
    }

}
