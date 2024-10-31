package com.dancestudio.erp.repository;


import com.dancestudio.erp.entity.Instructor;
import com.dancestudio.erp.enums.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query("SELECT s FROM Instructor s WHERE s.studio.id = :studioId AND (:membershipStatus IS NULL OR s.status = :membershipStatus)")
    List<Instructor> findAllByStudioId(@Param("studioId") Long studioId, @Param("membershipStatus") MembershipStatus membershipStatus);
}

