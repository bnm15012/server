package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.modules.member.memberActiveStatus.MinMax;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentActivityAssignmentRepository extends JpaRepository<StudentActivityAssignment, Long>, JpaSpecificationExecutor<StudentActivityAssignment> {

    @Query("SELECT a FROM StudentActivityAssignment a WHERE a.student.id = :studentId AND a.activityName = :activityName")
    StudentActivityAssignment findByStudentIdAndActivityId(@Param("studentId") Long studentId,
            @Param("activityName") String activityName);

    @Query("SELECT a FROM StudentActivityAssignment a WHERE a.student.id = :studentId")
    List<StudentActivityAssignment> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM StudentActivityAssignment a WHERE (:activityName IS NULL OR :activityName = '' OR a.activityName = :activityName) AND a.student.branch.id = :studioId")
    List<StudentActivityAssignment> findStudentsWithActiveMemberships(@Param("activityName") String activityName,
            @Param("studioId") Long studioId);

    @Query("SELECT a.student FROM StudentActivityAssignment a WHERE a.membershipEndDate <= :reminderDate")
    List<Long> findStudentIdsWithMembershipEndingOnDate(@Param("reminderDate") Date reminderDate);

    @Query("SELECT COUNT(s) FROM StudentActivityAssignment s WHERE s.student.branch.id = :studioId AND CURRENT_DATE >= DATE(s.membershipStartDate) AND CURRENT_DATE <= DATE(s.membershipEndDate)")
    long totalStudentActiveMembershipByStudioId(@Param("studioId") Long studioId);

    @Query("SELECT new com.dancestudio.erp.entry.MonthlyReportEntry(MONTH(s.registrationDate), SUM(s.activityAmount)) FROM StudentActivityAssignment s "
            +
            "WHERE YEAR(s.registrationDate) = :year AND s.student.branch.id = :studioId GROUP BY MONTH(s.registrationDate)")
    List<MonthlyReportEntry> getAnalysisReport(@Param("year") int year, @Param("studioId") Long studioId);

    @Query("SELECT a FROM StudentActivityAssignment a WHERE a.student.id = :studentId")
    Page<StudentActivityAssignment> findActivitiesByStudentId(
            @Param("studentId") Long studentId,
            Pageable pageable);


    @Query("""
        SELECT  new com.dancestudio.erp.modules.member.memberActiveStatus.MinMax(
            MIN(a.membershipStartDate) AS minDate,
            MAX(a.membershipEndDate)   AS maxDate
        )
        FROM StudentActivityAssignment a 
        WHERE a.student.id = :memberId
    """)
    MinMax findMinMaxWindow(Long memberId);

    // @Query("""
    //     SELECT s
    //     FROM StudentActivityAssignment s
    //     JOIN FETCH s.student st
    //     WHERE s.membershipEndDate IS NULL
    //     OR s.membershipEndDate >= CURRENT_DATE
    //     ORDER BY st.id, s.membershipStartDate, s.membershipEndDate
    //     """)
    // List<StudentActivityAssignment> findAllForMemberActiveStatusCache();
}
