package com.dancestudio.erp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dancestudio.erp.modules.genericTemplate.GenericTemplate;

@Repository
public interface GenricTemplateRepository extends JpaRepository<GenericTemplate, Long> {

    @Query("""
        SELECT c 
        FROM GenericTemplate c 
        WHERE c.studio.id = :studioId
          AND (:templateType IS NULL OR c.templateType LIKE %:templateType%)
    """)
    Page<GenericTemplate> findByStudioIdAndFilters(
        @Param("studioId") Long studioId,
        @Param("templateType") String templateType,
        Pageable pageable
    );

    @Query("""
        SELECT COUNT(c) 
        FROM GenericTemplate c 
        WHERE c.studio.id = :studioId
          AND (:templateType IS NULL OR c.templateType LIKE %:templateType%)
    """)
    long countByStudioIdAndFilters(
        @Param("studioId") Long studioId,
        @Param("templateType") String templateType
    );
}
