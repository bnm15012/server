package com.dancestudio.erp.repository;


import com.dancestudio.erp.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}

