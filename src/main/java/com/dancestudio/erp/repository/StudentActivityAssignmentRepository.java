package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.StudentActivityAssignment;
import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.ReportEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StudentActivityAssignmentRepository extends JpaRepository<StudentActivityAssignment, Long> {

    @Query("SELECT a FROM StudentActivityAssignment a WHERE a.student.id = :studentId AND a.activity.id = :activityId")
    StudentActivityAssignment findByStudentIdAndActivityId(@Param("studentId") Long studentId,
            @Param("activityId") Long activityId);

    @Query("SELECT a FROM StudentActivityAssignment a WHERE a.student.id = :studentId")
    List<StudentActivityAssignment> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM StudentActivityAssignment a WHERE (:activityId IS NULL OR a.activity.id = :activityId) AND a.student.studio.id = :studioId")
    List<StudentActivityAssignment> findStudentsWithActiveMemberships(@Param("activityId") Long activityId, @Param("studioId") Long studioId);

    @Query("SELECT a.student FROM StudentActivityAssignment a WHERE a.membershipEndDate <= :reminderDate")
    List<Long> findStudentIdsWithMembershipEndingOnDate(@Param("reminderDate") Date reminderDate);

    @Query("SELECT COUNT(s) FROM StudentActivityAssignment s WHERE s.student.studio.id = :studioId AND CURRENT_DATE BETWEEN s.membershipStartDate AND s.membershipEndDate")
    long totalStudentActiveMembershipByStudioId(@Param("studioId") Long studioId);

    @Query("SELECT new com.dancestudio.erp.entry.MonthlyReportEntry(MONTH(s.registrationDate), SUM(s.activityAmount)) FROM StudentActivityAssignment s " +
          "WHERE YEAR(s.registrationDate) = :year AND s.student.studio.id = :studioId GROUP BY MONTH(s.registrationDate)")
    List<MonthlyReportEntry> getAnalysisReport(@Param("year") int year, @Param("studioId") Long studioId);

}
