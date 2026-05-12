package com.dancestudio.erp.modules.membershipPackage;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.dao.DataIntegrityViolationException;
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
   private final List<String> predefinedMemberShipTypes = List.of("REGISTRATION", "MONTHLY", "QUARTERLY", "HALF_YEARLY",
         "YEARLY");

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

   @Override
   public MembershipPackagesEntry add(MembershipPackagesEntry entry)
         throws EntityNotFoundException, BeansException, Exception {
      validate(entry);
      try {
         return super.add(entry);
      } catch (DataIntegrityViolationException e) {

         if (e.getRootCause() != null &&
               e.getRootCause().getMessage().contains("membership_type_studio_key")) {

            throw new RuntimeException(
                  entry.getMembershipPackage() +
                        " already exists. Use another package name");
         }

         throw e;
      }
   }

   @Override
   public MembershipPackagesEntry update(Long id, MembershipPackagesEntry entry)
         throws EntityNotFoundException, BeansException, Exception {
      validate(entry);
      try {
         return super.update(id, entry);
      } catch (DataIntegrityViolationException e) {

         if (e.getRootCause() != null &&
               e.getRootCause().getMessage().contains("membership_type_studio_key")) {

            throw new RuntimeException(
                  entry.getMembershipPackage() +
                        " already exists. Use another package name");
         }

         throw e;
      }
   }

   private void validate(MembershipPackagesEntry entry) {
      if (entry.getDays() > 368) {
         throw new RuntimeException("Days cannot be more than 368");
      }
      if (predefinedMemberShipTypes.contains(entry.getMembershipPackage())) {
         throw new RuntimeException(entry.getMembershipPackage() + " is predefined membership type.");
      }
   }
}
