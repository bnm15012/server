package com.dancestudio.erp.manager;

import org.springframework.data.domain.Page;

import com.dancestudio.erp.entity.activity.MembershipPackages;
import com.dancestudio.erp.entry.activity.MembershipPackagesEntry;

public interface MembershipPackagesManager extends BaseManager<MembershipPackagesEntry, Long> {

   public Page<MembershipPackages> getAllPackagesByStudioId(Long studioId, Integer page, Integer size);
}
