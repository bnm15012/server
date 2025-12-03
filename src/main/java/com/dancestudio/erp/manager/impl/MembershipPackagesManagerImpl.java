package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.converter.MembershipPackagesConverter;
import com.dancestudio.erp.entry.activity.MembershipPackagesEntry;
import com.dancestudio.erp.entity.activity.MembershipPackages;
import com.dancestudio.erp.manager.MembershipPackagesManager;
import com.dancestudio.erp.repository.Activity.MembershipPackagesRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MembershipPackagesManagerImpl implements MembershipPackagesManager {

    private final MembershipPackagesRepository repository;

    public MembershipPackagesManagerImpl(MembershipPackagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public MembershipPackagesEntry add(MembershipPackagesEntry entry) throws Exception {
        MembershipPackages entity = MembershipPackagesConverter.toEntity(entry, null);
        MembershipPackages saved = repository.save(entity);
        return MembershipPackagesConverter.toEntry(saved);
    }

    @Override
    public MembershipPackagesEntry update(Long id, MembershipPackagesEntry entry) throws Exception {
        Optional<MembershipPackages> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new Exception("ActivityMembershipType with id " + id + " not found");
        }

        MembershipPackages toUpdate = existing.get();
        MembershipPackagesConverter.toEntity(entry, toUpdate);
        MembershipPackages saved = repository.save(toUpdate);
        return MembershipPackagesConverter.toEntry(saved);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("ActivityMembershipType with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    @Override
    public MembershipPackagesEntry getById(Long id) throws Exception {
        MembershipPackages entity = repository.findById(id)
                .orElseThrow(() -> new Exception("ActivityMembershipType with id " + id + " not found"));
        return MembershipPackagesConverter.toEntry(entity);
    }

    @Override
    public Page<MembershipPackages> getAllPackagesByStudioId(Long studioId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return repository.findByStudio_Id(studioId, pageable);
    }
}
