package com.dancestudio.erp.repository;


import com.dancestudio.erp.entity.InstructorActivityAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorActivityAssignmentRepository extends JpaRepository<InstructorActivityAssignment, Long> {

    @Query("SELECT a FROM InstructorActivityAssignment a WHERE a.instructor.id = :instructorId AND a.activity.id = :activityId")
    InstructorActivityAssignment findByInstructorIdAndActivityId(@Param("instructorId") Long instructorId, @Param("activityId") Long activityId);

    @Query("SELECT a FROM InstructorActivityAssignment a WHERE a.instructor.id = :instructorId")
    List<InstructorActivityAssignment> findByInstructorId(@Param("instructorId") Long instructorId);

}

