package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Branch;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findByStudioId(Long studioId);

    interface WhatsAppStatusView {
        String getWhatsappStatus();
    }

    Optional<WhatsAppStatusView> findProjectedById(Long branchId);

    @EntityGraph(attributePaths = "studio")
    Optional<Branch> findWithStudioById(Long branchId);

    @Transactional
    @Modifying
    @Query("UPDATE Branch b SET b.whatsappStatus = :status WHERE b.id = :branchId")
    int updateWhatsAppStatus(Long branchId, String status);
}
