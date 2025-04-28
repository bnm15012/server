package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findByStudioId(Long studioId);

}
