package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StudioResponse;
import com.dancestudio.erp.service.StudioService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class StudioServiceImpl implements StudioService {

    private StudioManager studioManager;

    @Override
    public StudioResponse addStudio(StudioEntry studioEntry) {
        StudioResponse response = new StudioResponse();

        StudioEntry entry = studioManager.addStudio(studioEntry);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public StudioResponse updateStudio(Long studioId, StudioEntry studioEntry) {
        StudioResponse response = new StudioResponse();

        StudioEntry entry = studioManager.updateStudio(studioId, studioEntry);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public StudioResponse deleteStudio(Long studioId) {
        StudioResponse response = new StudioResponse();
        try {
            Boolean isDeleted = studioManager.deleteStudio(studioId);
            response.setStatus(new StatusResponse(1, "Studio removed Successfully", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }
        return response;
    }

    @Override
    public StudioResponse getStudioById(Long studioId) {
        StudioResponse response = new StudioResponse();

        StudioEntry entry = studioManager.getStudioById(studioId);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public StudioResponse getAllStudios() {
        StudioResponse response = new StudioResponse();

        List<StudioEntry> entry = studioManager.getAllStudios();
        response.setData(entry);
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : entry.size()));

        return response;
    }
}
