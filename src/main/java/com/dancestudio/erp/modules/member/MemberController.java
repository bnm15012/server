package com.dancestudio.erp.modules.member;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dancestudio.erp.google.TokenRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/get")
    public org.springframework.http.ResponseEntity<MemberResponse> getMemberData() {
        return memberService.getMemberData();
    }

    @PostMapping("/google/login")
    public ResponseEntity<MemberResponse> login(@RequestBody TokenRequest request) {
        return memberService.memberLogin(request);
    }

    @GetMapping("/activities")
    public ResponseEntity<MemberActivityResponse> getActivityAssignment(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return memberService.getMemberActivityData(page, size);
    }
    
}
