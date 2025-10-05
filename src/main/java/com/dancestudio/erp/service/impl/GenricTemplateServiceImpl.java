package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.GenricTemplateEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.GenricTemplateManager;
import com.dancestudio.erp.manager.TemplateManager;
import com.dancestudio.erp.response.GenericTemplateResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.GenricTemplateService;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class GenricTemplateServiceImpl implements GenricTemplateService {

    private GenricTemplateManager conditionsManager;

    @Autowired
    private TemplateManager templateManager;

    @Override
    public ResponseEntity<GenericTemplateResponse> add(GenricTemplateEntry genericTemplateEntry) {
        GenericTemplateResponse response = new GenericTemplateResponse();

        try {
            GenricTemplateEntry entry = conditionsManager.add(genericTemplateEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Conditions added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<GenericTemplateResponse> update(Long id, GenricTemplateEntry genericTemplateEntry) {
        GenericTemplateResponse response = new GenericTemplateResponse();

        try {
            GenricTemplateEntry entry = conditionsManager.update(id, genericTemplateEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(
                    new StatusResponse(1, "Conditions updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        try {
            conditionsManager.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<GenericTemplateResponse> get(Long id) {
        GenericTemplateResponse response = new GenericTemplateResponse();

        try {
            GenricTemplateEntry entry = conditionsManager.getById(id);
            response.setData(Collections.singletonList(entry));
            response.setStatus(
                    new StatusResponse(1, "Conditions retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<GenericTemplateResponse> getAllByStudioId(Long studioId, String templateType, int page,
            int size) {
        GenericTemplateResponse response = new GenericTemplateResponse();
        List<GenricTemplateEntry> entries = new ArrayList<>();
        int templateSize = 0;

        try {
            // Get GenericTemplate entries
            entries.addAll(conditionsManager.getAllConditionsByStudioId(studioId, templateType, --page, size));

            // Merge with TemplateEntry list (mapped to GenricTemplateEntry)
            if (templateType.equalsIgnoreCase("COMMUNICATION")) {
                List<TemplateEntry> templates = templateManager.getAllTemplates(studioId);
                entries.addAll(mapTemplatesToGenericEntries(templates, studioId));
                templateSize = templates.size();
            }

            // Count from both sources
            long totalCount = conditionsManager.countByFilters(studioId, templateType) + templateSize;

            // Set response
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Templates merged successfully",
                    StatusResponse.Type.SUCCESS, (int) totalCount));

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Maps a list of TemplateEntry to GenricTemplateEntry.
     */
    private List<GenricTemplateEntry> mapTemplatesToGenericEntries(List<TemplateEntry> templates, Long studioId) {
        List<GenricTemplateEntry> mappedList = new ArrayList<>();
        for (TemplateEntry t : templates) {
            GenricTemplateEntry entry = new GenricTemplateEntry();
            entry.setTemplateType("COMMUNICATION");
            entry.setTemplateName(t.getTemplateName());
            entry.setTemplateSubject(t.getSubject());
            entry.setTemplateContent(t.getTemplateBody());
            entry.setStudioId(studioId);
            mappedList.add(entry);
        }
        return mappedList;
    }

}
