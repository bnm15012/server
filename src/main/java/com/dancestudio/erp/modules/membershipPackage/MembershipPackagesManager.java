package com.dancestudio.erp.modules.membershipPackage;

import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.exception.EntityNotFoundException;

import lombok.Setter;

@Service
@Setter
public class MembershipPackagesManager extends BaseManager<MembershipPackages, Long, MembershipPackagesEntry> {

   private final MembershipPackagesRepository repository;

   protected MembershipPackagesManager(MembershipPackagesRepository repository) {
      super(repository, "Membership Type");
      this.repository = repository;
   }

   public Page<MembershipPackagesEntry> getAllPackagesByStudioId(Long studioId, Integer page, Integer size) {

      Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

      return repository.findByStudio_Id(studioId, pageable).map(MembershipPackagesConverter::toEntry);
   }

   @Override
   protected MembershipPackages toEntity(MembershipPackagesEntry entry, MembershipPackages existing) throws BeansException, Exception {
      return MembershipPackagesConverter.toEntity(entry, existing);
   }

   @Override
   protected MembershipPackagesEntry toEntry(MembershipPackages entity) throws EntityNotFoundException {
      return MembershipPackagesConverter.toEntry(entity);
   }
}
