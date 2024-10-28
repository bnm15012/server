package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
