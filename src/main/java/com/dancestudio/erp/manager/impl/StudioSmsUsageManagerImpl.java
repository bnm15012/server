package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entity.StudioSmsUsage;
import com.dancestudio.erp.entry.StudioSmsUsageEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudioSmsUsageManager;
import com.dancestudio.erp.repository.BranchRepository;
import com.dancestudio.erp.repository.StudioSmsUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StudioSmsUsageManagerImpl implements StudioSmsUsageManager {

    private final StudioSmsUsageRepository studioSmsUsageRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public StudioSmsUsageManagerImpl(StudioSmsUsageRepository studioSmsUsageRepository, BranchRepository branchRepository) {
        this.studioSmsUsageRepository = studioSmsUsageRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public StudioSmsUsageEntry add(StudioSmsUsageEntry studioSmsUsageEntry) throws Exception {

        StudioSmsUsage usage = studioSmsUsageRepository.findByBranchIdAndMonth(studioSmsUsageEntry.getBranchId(), studioSmsUsageEntry.getMonth()).orElse(null);
        if (Objects.nonNull(usage)) {
            throw new Exception("Entry already exists for given month and branchId");
        }

        StudioSmsUsage studioSmsUsage = convertToEntity(studioSmsUsageEntry, null);
        return convertToEntry(studioSmsUsageRepository.save(studioSmsUsage));
    }

    @Override
    public StudioSmsUsageEntry update(Long studioSmsUsageId, StudioSmsUsageEntry studioSmsUsageEntry) throws Exception {
        StudioSmsUsage existingStudioSmsUsage = studioSmsUsageRepository.findById(studioSmsUsageId)
                .orElseThrow(() -> new EntityNotFoundException("Studio Sms Usage not found"));

        StudioSmsUsage updatedStudioSmsUsage = convertToEntity(studioSmsUsageEntry, existingStudioSmsUsage);
        return convertToEntry(studioSmsUsageRepository.save(updatedStudioSmsUsage));
    }

    @Override
    public void delete(Long studioSmsUsageId) throws EntityNotFoundException {
        studioSmsUsageRepository.findById(studioSmsUsageId)
                .orElseThrow(() -> new EntityNotFoundException("Studio Sms Usage not found"));

        studioSmsUsageRepository.deleteById(studioSmsUsageId);
    }

    @Override
    public StudioSmsUsageEntry getById(Long studioSmsUsageId) throws EntityNotFoundException {
        StudioSmsUsage studioSmsUsage = studioSmsUsageRepository.findById(studioSmsUsageId)
                .orElseThrow(() -> new EntityNotFoundException("Studio Sms Usage not found"));

        return convertToEntry(studioSmsUsage);
    }


    private  StudioSmsUsageEntry convertToEntry(StudioSmsUsage studioSmsUsage) {
        if (Objects.isNull(studioSmsUsage)) {
            return null;
        }

        StudioSmsUsageEntry studioSmsUsageEntry = new StudioSmsUsageEntry();
        studioSmsUsageEntry.setId(studioSmsUsage.getId());
        studioSmsUsageEntry.setBranchId(studioSmsUsage.getBranch().getId());
        studioSmsUsageEntry.setMonth(studioSmsUsage.getMonth());
        studioSmsUsageEntry.setTotalSmsSent(studioSmsUsage.getTotalSmsSent());
        studioSmsUsageEntry.setQuota(studioSmsUsage.getQuota());

        return studioSmsUsageEntry;
    }

    private StudioSmsUsage convertToEntity(StudioSmsUsageEntry studioSmsUsageEntry, StudioSmsUsage existingStudioSmsUsage) throws Exception {
        StudioSmsUsage studioSmsUsage = existingStudioSmsUsage != null ? existingStudioSmsUsage : new StudioSmsUsage();

        studioSmsUsage.setId(studioSmsUsageEntry.getId());
        studioSmsUsage.setMonth(studioSmsUsageEntry.getMonth());
        studioSmsUsage.setTotalSmsSent(studioSmsUsageEntry.getTotalSmsSent());
        studioSmsUsage.setQuota(studioSmsUsageEntry.getQuota());

        Branch branch = branchRepository.findById(studioSmsUsageEntry.getBranchId()).orElseThrow(() -> new EntityNotFoundException("Branch not found"));
        studioSmsUsage.setBranch(branch);

        return studioSmsUsage;
    }

}
