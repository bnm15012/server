package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.StudentActivityAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentActivityAssignmentRepository extends JpaRepository<StudentActivityAssignment, Long> {

    @Query("SELECT a FROM StudentActivityAssignment a WHERE a.studentId = :studentId AND a.activityId = :activityId")
    StudentActivityAssignment findByStudentIdAndActivityId(@Param("studentId") Long studentId, @Param("activityId") Long activityId);

}
