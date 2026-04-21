package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class StudentService extends BaseService<StudentEntry, Long>{

    private StudentManager studentManager;

    public ResponseEntity<StudentResponse> getAllStudents(Long branchId, String activityName, MembershipStatus membershipStatus, int page, int size, String searchTerm) {
        StudentResponse response = new StudentResponse();

        try {
            Page<StudentEntry> entries = studentManager.getAllStudentsByStudio(branchId, activityName, membershipStatus, --page, size, searchTerm);
            response.setData(entries.getContent());
            response.setStatus(new StatusResponse(1, "Students retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<StudentCommunicationResponse> getAllStudentsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size, int birthday) {
        StudentCommunicationResponse response = new StudentCommunicationResponse();

        try {
            Page<StudentCommunicationEntry> entries = studentManager.getAllStudentsForCommunication(branchId, membershipStatus, --page, size, birthday);
            response.setData(entries.getContent());
            response.setStatus(new StatusResponse(1, "Students retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(Long studentId, String activityName) {
        StudentResponse response = new StudentResponse();
        try {
            boolean reminderSent = studentManager.sendSubscriptionRenewalReminder(studentId, activityName);

            response.setStatus(new StatusResponse(0, "Reminder not sent", StatusResponse.Type.ERROR));
            if (reminderSent) {
                response.setStatus(new StatusResponse(1, "Reminder sent successfully", StatusResponse.Type.SUCCESS));
            }
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    protected StudentEntry doAdd(StudentEntry entry) throws Exception {
        return studentManager.add(entry);
    }

    @Override
    protected StudentEntry doUpdate(Long id, StudentEntry entry) throws Exception {
        return studentManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        studentManager.delete(id);
    }

    @Override
    protected StudentEntry doGet(Long id) throws Exception {
        return studentManager.getById(id);
    }

}
