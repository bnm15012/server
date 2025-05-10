package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.MessageRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRecipientRepository extends JpaRepository<MessageRecipient, Long> {

    List<MessageRecipient> findByMessageId(Long messageId);
}
