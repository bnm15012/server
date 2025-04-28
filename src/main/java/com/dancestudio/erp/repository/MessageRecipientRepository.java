package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.MessageRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRecipientRepository extends JpaRepository<MessageRecipient, Long> {
}
