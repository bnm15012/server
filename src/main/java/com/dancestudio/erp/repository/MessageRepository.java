package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByBranchId(Long branchId);

    Page<Message> findByBranchId(Long branchId, Pageable pageable);

}
