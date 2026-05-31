package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.WhatsAppStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.modules.branch.BranchConvertor;
import com.dancestudio.erp.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BranchManagerImpl implements BranchManager {

    private final BranchRepository branchRepository;

    @Autowired
    private UserManager userManager;

    @Autowired
    public BranchManagerImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public BranchEntry add(BranchEntry branchEntry) throws Exception {
        if (Objects.isNull(branchEntry.getWhatsAppStatus())) {
            branchEntry.setWhatsAppStatus(WhatsAppStatus.INACTIVE);
        }
        Branch branch = BranchConvertor.convertToEntity(branchEntry, null);
        return BranchConvertor.convertToEntry(branchRepository.save(branch));
    }

    @Override
    public BranchEntry update(Long branchId, BranchEntry branchEntry) throws Exception {
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

        Branch updatedBranch = BranchConvertor.convertToEntity(branchEntry, existingBranch);
        return BranchConvertor.convertToEntry(branchRepository.save(updatedBranch));
    }

    @Override
    public void delete(Long branchId) throws EntityNotFoundException {
        branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

        branchRepository.deleteById(branchId);
    }

    @Override
    public BranchEntry getById(Long branchId) throws Exception {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

        return BranchConvertor.convertToEntry(branch);
    }

    @Override
    public List<BranchEntry> findByStudioId(Long studioId) throws Exception {
        List<Branch> branchList = branchRepository.findByStudioId(studioId);
        return branchList.stream()
                .map(BranchConvertor::convertToEntry)
                .toList();
    }

    @Override
    public BranchEntry enableDisableBranch(Long branchId, boolean flag) throws Exception {
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

        if (flag) {
            existingBranch.setIsActive(true);
        }

        else {
            existingBranch.setIsActive(false);
            List<UserEntry> userEntries = userManager.getUserByBranchId(branchId);
            for (UserEntry userEntry : userEntries) {
                userEntry.setEnabled(false);
                userManager.update(userEntry.getUserId(), userEntry);
            }
        }
        return BranchConvertor.convertToEntry(branchRepository.save(existingBranch));
    }
}
