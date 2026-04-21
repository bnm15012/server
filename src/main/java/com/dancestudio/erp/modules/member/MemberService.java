package com.dancestudio.erp.modules.member;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.authentication.SpringSecurityUtil;
import com.dancestudio.erp.google.GoogleTokenVerifierService;
import com.dancestudio.erp.google.GoogleUser;

import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.google.TokenRequest;
import com.dancestudio.erp.response.StatusResponse;

@Component
public class MemberService {

    private final JwtUtil jwtUtil;
    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final MemberManager memberManager;
    private final SpringSecurityUtil springSecurityUtil;

    public MemberService(MemberManager memberManager, GoogleTokenVerifierService googleTokenVerifierService,
            SpringSecurityUtil springSecurityUtil, JwtUtil jwtUtil) {
        this.memberManager = memberManager;
        this.googleTokenVerifierService = googleTokenVerifierService;
        this.springSecurityUtil = springSecurityUtil;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<MemberResponse> getMemberData() {
        String email = springSecurityUtil.getUserEmail();
        return generateData(email, false);
    }

    private ResponseEntity<MemberResponse> generateData(String email, Boolean generateToken) {
        MemberResponse response = new MemberResponse();
        try {
            if (generateToken) {
                String accessToken = jwtUtil.generateAccessToken(email);
                response.setToken(accessToken);
            }
            response.setData(Collections.singletonList(memberManager.getMemberData(email)));
            response.setStatus(new StatusResponse(1, "Students retrieved successfully", StatusResponse.Type.SUCCESS,
                    1));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<MemberResponse> memberLogin(TokenRequest request) {
        GoogleUser user = googleTokenVerifierService.verify(request.getIdToken());
        return generateData(user.getEmail(), true);
    }

    public ResponseEntity<MemberActivityResponse> getMemberActivityData(Integer page, Integer size) {
        String email = springSecurityUtil.getUserEmail();
        Member member = memberManager.findByEmail(email);
        MemberActivityResponse response = new MemberActivityResponse();
        try {
            Page<MemberActivityAssignmentEntry> memberActivityAssignmentEntries = memberManager.getMemberActivityAssignmentEntries(member, page, size);
            response.setData(memberActivityAssignmentEntries.getContent());
            response.setStatus(new StatusResponse(1, "retrieved successfully", StatusResponse.Type.SUCCESS,
                   memberActivityAssignmentEntries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
