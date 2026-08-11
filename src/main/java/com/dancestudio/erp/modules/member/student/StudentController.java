package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.enums.MembershipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController extends BaseController<StudentEntry, Long> {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<StudentResponse> getAllStudents(
            @PathVariable Long branchId,
            @RequestParam(required = false) String activityName,
            @RequestParam(required = false) MembershipStatus membershipStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "-1") int size,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        return studentService.getAllStudents(branchId, activityName, membershipStatus, page, size, searchTerm, isActive);
    }

    @GetMapping("/getAllStudentsForCommunication/{branchId}")
    public ResponseEntity<StudentCommunicationResponse> getAllStudents(
            @PathVariable Long branchId,
            @RequestParam(required = false) MembershipStatus membershipStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "-1") int size,
            @RequestParam(defaultValue = "0") int birthday) {
        return studentService.getAllStudentsForCommunication(branchId, membershipStatus, page, size, birthday);
    }

    @PostMapping("/sendSubscriptionRenewalReminder/{studentId}/{activityName}")
    public ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(@PathVariable Long studentId,
            @PathVariable String activityName) {
        return studentService.sendSubscriptionRenewalReminder(studentId, activityName);
    }

    @Override
    protected BaseService<StudentEntry, Long> getService() {
        return studentService;
    }
}
