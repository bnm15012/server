package com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorActivityAssignmentRepository extends JpaRepository<InstructorActivityAssignment, Long> {

    @Query("SELECT a FROM InstructorActivityAssignment a WHERE a.instructor.id = :instructorId AND a.activityName = :activityName")
    InstructorActivityAssignment findByInstructorIdAndActivityId(@Param("instructorId") Long instructorId,
            @Param("activityName") String activityName);

    @Query("SELECT a FROM InstructorActivityAssignment a WHERE a.instructor.id = :instructorId")
    Page<InstructorActivityAssignment> findByInstructorId(
            @Param("instructorId") Long instructorId,
            Pageable pageable);

    @Query("""
                SELECT
                    MIN(a.membershipStartDate) AS earliestStart,
                    CASE
                        WHEN COUNT(a.membershipEndDate) < COUNT(*)
                            THEN NULL
                            ELSE MAX(a.membershipEndDate)
                    END AS latestEnd
                FROM StudentActivityAssignment a
                WHERE a.student.id = :memberId
            """)
    Object[] findMinAndCustomMax(Long memberId);

}
