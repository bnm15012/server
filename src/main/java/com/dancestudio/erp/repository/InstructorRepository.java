package com.dancestudio.erp.repository;


import com.dancestudio.erp.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query("SELECT s FROM Instructor s WHERE s.studio.id = :studioId")
    List<Instructor> findAllByStudioId(@Param("studioId") Long studioId);
}

