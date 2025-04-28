package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.TemplateManager;
import com.dancestudio.erp.response.TemplateResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.TemplateService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class TemplateServiceImpl implements TemplateService {

    private TemplateManager templateManager;

    @Override
    public ResponseEntity<TemplateResponse> add(TemplateEntry templateEntry) {
        TemplateResponse response = new TemplateResponse();

        try {
            TemplateEntry entry = templateManager.add(templateEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Bank Account added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<TemplateResponse> update(Long templateId, TemplateEntry templateEntry) {
        TemplateResponse response = new TemplateResponse();

        try {
            TemplateEntry entry = templateManager.update(templateId, templateEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Bank Account updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long templateId) {
        try {
            templateManager.delete(templateId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<TemplateResponse> get(Long templateId) {
        TemplateResponse response = new TemplateResponse();

        try {
            TemplateEntry entry = templateManager.getById(templateId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Bank Account retrieved successfully", StatusResponse.Type.SUCCESS, 1));
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
}
