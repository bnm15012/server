package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.MembershipFeeManager;
import com.dancestudio.erp.response.MembershipFeeResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.MembershipFeeService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class MembershipFeeServiceImpl implements MembershipFeeService {

    private MembershipFeeManager membershipFeeManager;

    @Override
    public ResponseEntity<MembershipFeeResponse> addMembershipFee(MembershipFeeEntry membershipFeeEntry) {
        MembershipFeeResponse response = new MembershipFeeResponse();
        try {
            MembershipFeeEntry entry = membershipFeeManager.addMembershipFee(membershipFeeEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Membership fee entry added successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, "An error occurred while adding the membership fee", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MembershipFeeResponse> updateMembershipFee(Long id, Double newFeeAmount) {
        MembershipFeeResponse response = new MembershipFeeResponse();
        try {
            MembershipFeeEntry entry = membershipFeeManager.updateMembershipFee(id, newFeeAmount);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Membership fee updated successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, "An error occurred while updating the membership fee", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MembershipFeeResponse> getMembershipFee(Long id) {
        MembershipFeeResponse response = new MembershipFeeResponse();
        try {
            MembershipFeeEntry entry = membershipFeeManager.getMembershipFeeById(id);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Membership fee retrieved successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, "An error occurred while retrieving the membership fee", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MembershipFeeResponse> getMembershipFeesByStudio(Long studioId) {
        MembershipFeeResponse response = new MembershipFeeResponse();
        try {
            List<MembershipFeeEntry> entries = membershipFeeManager.getMembershipFeesByStudio(studioId);

            response.setData(entries);
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : entries.size()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
