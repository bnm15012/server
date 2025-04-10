package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT s FROM Client s WHERE s.studio.id = :studioId and month(s.createdOn) >= :startMonth and month(s.createdOn) <= :endMonth")
    Page<Client> findAllByStudioId(@Param("studioId") Long studioId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth, Pageable pageable);

    @Query("SELECT s FROM Client s WHERE s.studio.id = :studioId")
    Page<Client> findClientsByStudioId(@Param("studioId") Long studioId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Client s WHERE s.studio.id = :studioId")
    Long countClientsByStudioId(@Param("studioId") Long studioId);

    @Query("SELECT COUNT(s) FROM Client s WHERE s.studio.id = :studioId and month(s.createdOn) >= :startMonth and month(s.createdOn) <= :endMonth")
    Long countClientsByStudioIdAndMonthLong(@Param("studioId") Long studioId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth);

    List<Client> findByGroupNameContainingIgnoreCase(String groupName);

}