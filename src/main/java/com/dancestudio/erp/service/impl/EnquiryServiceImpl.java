package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.EnquiryEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EnquiryManager;
import com.dancestudio.erp.response.EnquiryResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.EnquiryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class EnquiryServiceImpl implements EnquiryService {

    private EnquiryManager enquiryManager;

    @Override
    public ResponseEntity<EnquiryResponse> add(EnquiryEntry enquiryEntry) {
        EnquiryResponse response = new EnquiryResponse();

        try {
            EnquiryEntry entry = enquiryManager.add(enquiryEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Enquiry added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<EnquiryResponse> update(Long enquiryId, EnquiryEntry enquiryEntry) {
        EnquiryResponse response = new EnquiryResponse();

        try {
            EnquiryEntry entry = enquiryManager.update(enquiryId, enquiryEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Enquiry updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long enquiryId) {
        try {
            enquiryManager.delete(enquiryId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<EnquiryResponse> get(Long enquiryId) {
        EnquiryResponse response = new EnquiryResponse();

        try {
            EnquiryEntry entry = enquiryManager.getById(enquiryId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Enquiry retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<EnquiryResponse> getAllEnquiries(Long branchId, Integer page, Integer size,
                                                           Integer startMonth, Integer startYear,
                                                           Integer endMonth, Integer endYear,
                                                           String searchTerm) {
        EnquiryResponse response = new EnquiryResponse();

        try {
            List<EnquiryEntry> entries = enquiryManager.getAllEnquiries(branchId, --page, size,
                    startMonth, startYear, endMonth, endYear, searchTerm);

            long enquiryCount = (Objects.nonNull(startMonth) && Objects.nonNull(startYear)
                    && Objects.nonNull(endMonth) && Objects.nonNull(endYear))
                    ? enquiryManager.countEnquiriesByBranchIdAndMonth(branchId, startMonth, startYear, endMonth, endYear, searchTerm)
                    : enquiryManager.countEnquiriesByBranchId(branchId);

            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Enquiries retrieved successfully", StatusResponse.Type.SUCCESS, (int) enquiryCount));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
