package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.manager.InstructorManager;
import com.dancestudio.erp.response.InstructorResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.InstructorService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class InstructorServiceImpl implements InstructorService {

    private InstructorManager instructorManager;

    @Override
    public InstructorResponse addInstructor(InstructorEntry instructorEntry) {
        InstructorResponse response = new InstructorResponse();

        InstructorEntry entry = instructorManager.addInstructor(instructorEntry);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public InstructorResponse updateInstructor(Long instructorId, InstructorEntry instructorEntry) {
        InstructorResponse response = new InstructorResponse();

        InstructorEntry entry = instructorManager.updateInstructor(instructorId, instructorEntry);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public void deleteInstructor(Long instructorId) {
        instructorManager.deleteInstructor(instructorId);
    }

    @Override
    public InstructorResponse getInstructorById(Long instructorId) {
        InstructorResponse response = new InstructorResponse();

        InstructorEntry entry = instructorManager.getInstructorById(instructorId);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public InstructorResponse getAllInstructors() {
        InstructorResponse response = new InstructorResponse();

        List<InstructorEntry> entry = instructorManager.getAllInstructors();
        response.setData(entry);
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : entry.size()));

        return response;
    }
}
