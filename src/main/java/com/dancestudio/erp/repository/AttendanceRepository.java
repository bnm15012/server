package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findByStudentIdAndActivityIdAndBranchIdAndStatus(Long studentId, Long activityId, Long branchId, String status);

    Attendance findByStudentIdAndActivityIdAndBranchId(Long studentId, Long activityId, Long branchId);

}
