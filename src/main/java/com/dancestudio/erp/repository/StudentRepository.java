package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.enums.MembershipStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByNameAndEmail(String name, String email);

    @Query("SELECT s FROM Student s WHERE s.studio.id = :studioId AND (:activityId IS NULL OR s.id IN " +
            "(SELECT sa.student.id FROM StudentActivityAssignment sa WHERE sa.activity.id = :activityId and sa.status = :membershipStatus))")
    List<Student> findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(@Param("studioId") Long studioId,
                                                         @Param("activityId") Long activityId,
                                                         @Param("membershipStatus") MembershipStatus membershipStatus);

    @Query("SELECT s FROM Student s WHERE s.studio.id = :studioId AND (:activityId IS NULL OR s.id IN " +
            "(SELECT sa.student.id FROM StudentActivityAssignment sa WHERE sa.activity.id = :activityId and sa.status = :membershipStatus))")
    Page<Student> findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(@Param("studioId") Long studioId, @Param("activityId") Long activityId,
            @Param("membershipStatus") MembershipStatus membershipStatus, Pageable pageable);

    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.studio.id = :studioId")
    boolean studentsExistsByStudioId(@Param("studioId") Long studioId);

}
