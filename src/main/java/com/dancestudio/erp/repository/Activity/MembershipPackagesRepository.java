package com.dancestudio.erp.repository.Activity;

import com.dancestudio.erp.entity.activity.MembershipPackages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipPackagesRepository extends JpaRepository<MembershipPackages, Long> {
    Page<MembershipPackages> findByStudio_Id(Long studioId, Pageable pageable);
}
