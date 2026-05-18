package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.modules.member.attendance.AttendanceReqDTO;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class StudentActivityAssignmentService extends BaseService<StudentActivityAssignmentEntry, Long> {

    private StudentActivityAssignmentManager studentActivityAssignmentManager;

    public ResponseEntity<StudentActivityAssignmentResponse> getAssignments(
            Long rootId,
            String rootType,
            com.dancestudio.erp.enums.ActivityType activityName,
            String searchText,
            java.time.LocalDate date,
            Integer page,
            Integer size) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();
        try {
            Page<StudentActivityAssignment> entries = studentActivityAssignmentManager.getAssignmentsByCriteria(
                    rootId, rootType, activityName, searchText, date, --page, size);
            response.setData(entries.getContent().stream().map(a -> {
                try {
                    return StudentActivityAssignmentConvertor.convertToEntry(a, new String[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
            response.setStatus(new StatusResponse(1, "Assignments retrieved successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entries) ? 0 : (int) entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    protected StudentActivityAssignmentEntry doAdd(StudentActivityAssignmentEntry entry) throws Exception {
        return studentActivityAssignmentManager.add(entry);
    }

    @Override
    protected StudentActivityAssignmentEntry doUpdate(Long id, StudentActivityAssignmentEntry entry) throws Exception {
        return studentActivityAssignmentManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        studentActivityAssignmentManager.delete(id);
    }

    @Override
    protected StudentActivityAssignmentEntry doGet(Long id) throws Exception {
        return studentActivityAssignmentManager.getById(id);
    }

    public ResponseEntity<StudentActivityAssignmentResponse> markAttendance(Long activityAssignmentId) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();
        try {
            StudentActivityAssignmentEntry activityAssignmentEntry = studentActivityAssignmentManager
                    .markAttendance(activityAssignmentId);
            response.setData(Collections.singletonList(activityAssignmentEntry));
            response.setStatus(new StatusResponse(1, "Attendance marked successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<StudentActivityAssignmentResponse> markAttendanceBulk(AttendanceReqDTO reqDTO) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();
        try {
            java.util.List<StudentActivityAssignmentEntry> activityAssignmentEntries = studentActivityAssignmentManager
                    .markAttendanceBulk(reqDTO);
            response.setData(activityAssignmentEntries);
            response.setStatus(new StatusResponse(1, "Bulk attendance marked successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
