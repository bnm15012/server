package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
