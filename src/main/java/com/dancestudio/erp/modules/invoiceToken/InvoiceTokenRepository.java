package com.dancestudio.erp.modules.invoiceToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceTokenRepository extends JpaRepository<InvoiceToken, String> {

    Optional<InvoiceToken> findByInvoiceToken(UUID token);

    @Modifying
    @Transactional
    int deleteByExpiresAtBefore(Date date);
}
