package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entity.StudentActivityAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StudentActivityAssignmentRepository extends JpaRepository<StudentActivityAssignment, Long> {

    @Query("SELECT a FROM StudentActivityAssignment a WHERE a.student.id = :studentId AND a.activityId = :activityId")
    StudentActivityAssignment findByStudentIdAndActivityId(@Param("studentId") Long studentId, @Param("activityId") Long activityId);

    @Query("SELECT s FROM StudentActivityAssignment a JOIN a.student s WHERE (:activityId IS NULL OR a.activityId = :activityId) " +
            "AND s.studioId = :studioId AND a.status = :status")
    List<Student> findByOptionalActivityIdAndStudioIdAndStatus(@Param("activityId") Long activityId,
                                                               @Param("studioId") Long studioId,
                                                               @Param("status") String status);

    @Query("SELECT a.student FROM StudentActivityAssignment a WHERE a.membershipEndDate <= :reminderDate")
    List<Long> findStudentIdsWithMembershipEndingOnDate(@Param("reminderDate") LocalDate reminderDate);

}
