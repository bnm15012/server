package com.dancestudio.erp.repository;


import com.dancestudio.erp.entity.Instructor;
import com.dancestudio.erp.enums.MembershipStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    Optional<Instructor> findByNameAndEmail(String name, String email);

    @Query("SELECT s FROM Instructor s WHERE s.studio.id = :studioId AND (:membershipStatus IS NULL OR s.status = :membershipStatus)")
    List<Instructor> findAllByStudioId(@Param("studioId") Long studioId, @Param("membershipStatus") MembershipStatus membershipStatus);


    @Query("SELECT i FROM Instructor i " +
            "WHERE i.studio.id = :studioId " +
            "AND (:activityId IS NULL OR i.id IN " +
            "(SELECT sa.instructor.id FROM InstructorActivityAssignment sa WHERE sa.activity.id = :activityId and sa.status = :membershipStatus))")
    List<Instructor> findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(@Param("studioId") Long studioId, @Param("activityId") Long activityId,
                                                                          @Param("membershipStatus") MembershipStatus membershipStatus);

    @Query("SELECT s FROM Instructor s " +
            "WHERE s.studio.id = :studioId " +
            "AND (:activityId IS NULL OR s.id IN " +
            "(SELECT sa.instructor.id FROM InstructorActivityAssignment sa WHERE sa.activity.id = :activityId and sa.status = :membershipStatus))")
    Page<Instructor> findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(@Param("studioId") Long studioId, @Param("activityId") Long activityId,
                                                                          @Param("membershipStatus") MembershipStatus membershipStatus, Pageable pageable);

}

