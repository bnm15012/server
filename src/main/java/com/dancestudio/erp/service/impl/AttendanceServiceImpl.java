package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.AttendanceEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.AttendanceManager;
import com.dancestudio.erp.response.AttendanceResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.AttendanceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class AttendanceServiceImpl implements AttendanceService {

    private AttendanceManager attendanceManager;

    @Override
    public ResponseEntity<AttendanceResponse> add(AttendanceEntry attendanceEntry) {
        AttendanceResponse response = new AttendanceResponse();

        try {
            AttendanceEntry entry = attendanceManager.add(attendanceEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Attendance added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<AttendanceResponse> update(Long attendanceId, AttendanceEntry attendanceEntry) {
        AttendanceResponse response = new AttendanceResponse();

        try {
            AttendanceEntry entry = attendanceManager.update(attendanceId, attendanceEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Attendance updated successfully", StatusResponse.Type.SUCCESS, 1));
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
    public ResponseEntity<Void> delete(Long attendanceId) {
        try {
            attendanceManager.delete(attendanceId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<AttendanceResponse> get(Long attendanceId) {
        AttendanceResponse response = new AttendanceResponse();

        try {
            AttendanceEntry entry = attendanceManager.getById(attendanceId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Attendance retrieved successfully", StatusResponse.Type.SUCCESS, 1));
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
    public ResponseEntity<AttendanceResponse> getAllAttendances(HttpServletRequest request) {
        AttendanceResponse response = new AttendanceResponse();

        try {
            List<AttendanceEntry> entries = attendanceManager.getAllAttendances(request);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Attendances retrieved successfully", StatusResponse.Type.SUCCESS, (int) entries.size()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
