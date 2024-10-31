package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.enums.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s " +
            "WHERE s.studio.id = :studioId " +
            "AND (:activityId IS NULL OR s.id IN " +
            "(SELECT sa.student.id FROM StudentActivityAssignment sa WHERE sa.activity.id = :activityId)) " +
            "AND (:membershipStatus IS NULL OR s.status = :membershipStatus)")
    List<Student> findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(@Param("studioId") Long studioId,
                                                         @Param("activityId") Long activityId,
                                                         @Param("membershipStatus") MembershipStatus membershipStatus);

    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.studio.id = :studioId")
    boolean studentsExistsByStudioId(@Param("studioId") Long studioId);

    @Query("SELECT s FROM Student s WHERE s.membershipEndDate = :reminderDate")
    List<Student> findByMembershipEndDate(LocalDate reminderDate);
}
