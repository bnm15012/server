package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long> {

    @Query(value = "select * from studio where name = :studioName", nativeQuery = true)
    Optional<Studio> findByName(String studioName);

}
