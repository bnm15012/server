package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.StudioSmsUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioSmsUsageRepository extends JpaRepository<StudioSmsUsage, Long> {

    Optional<StudioSmsUsage> findByBranchIdAndMonth(Long branchId, Long month);


}
