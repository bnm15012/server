package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudioRepository extends JpaRepository<Studio, Long> {

    @Override
    @Query("DELETE FROM Studio s WHERE s.id = :studioId")
    void deleteById(@Param("studioId") Long studioId);

}
