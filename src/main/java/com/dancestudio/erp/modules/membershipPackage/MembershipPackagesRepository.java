package com.dancestudio.erp.modules.membershipPackage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipPackagesRepository extends JpaRepository<MembershipPackages, Long> {
    Page<MembershipPackages> findByStudio_Id(Long studioId, Pageable pageable);
}
