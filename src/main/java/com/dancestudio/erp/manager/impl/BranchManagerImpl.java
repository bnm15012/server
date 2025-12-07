package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.WhatsAppStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.modules.member.student.StudioManagerImpl;
import com.dancestudio.erp.repository.BranchRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BranchManagerImpl implements BranchManager, ApplicationContextAware {

    private final BranchRepository branchRepository;
    private static ApplicationContext applicationContext;

    @Autowired
    private UserManager userManager;

    @Autowired
    public BranchManagerImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        BranchManagerImpl.applicationContext = applicationContext;
    }

    @Override
    public BranchEntry add(BranchEntry branchEntry) throws Exception {
        if (Objects.isNull(branchEntry.getWhatsAppStatus())) {
            branchEntry.setWhatsAppStatus(WhatsAppStatus.INACTIVE);
        }
        Branch branch = convertToEntity(branchEntry, null);
        return convertToEntry(branchRepository.save(branch));
    }

    @Override
    public BranchEntry update(Long branchId, BranchEntry branchEntry) throws Exception {
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

        Branch updatedBranch = convertToEntity(branchEntry, existingBranch);
        return convertToEntry(branchRepository.save(updatedBranch));
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

        return convertToEntry(branch);
    }

    @Override
    public List<BranchEntry> findByStudioId(Long studioId) throws Exception {
        List<Branch> branchList = branchRepository.findByStudioId(studioId);
        return branchList.stream()
                .map(this::convertToEntry)
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
        return convertToEntry(branchRepository.save(existingBranch));
    }

    private Branch convertToEntity(BranchEntry branchEntry, Branch existingBranch) throws Exception {

        Branch branch = (existingBranch != null) ? existingBranch : new Branch();

        if (branchEntry.getName() != null) {
            branch.setName(branchEntry.getName());
        }
        if (branchEntry.getAddress() != null) {
            branch.setAddress(branchEntry.getAddress());
        }
        if (branchEntry.getCity() != null) {
            branch.setCity(branchEntry.getCity());
        }
        if (branchEntry.getState() != null) {
            branch.setState(branchEntry.getState());
        }
        if (branchEntry.getPincode() != null) {
            branch.setPincode(branchEntry.getPincode());
        }
        if (branchEntry.getPhone() != null) {
            branch.setPhone(branchEntry.getPhone());
        }
        if (branchEntry.getIsActive() != null) {
            branch.setIsActive(branchEntry.getIsActive());
        }
        if (Objects.nonNull(branchEntry.getWhatsAppStatus())) {
            branch.setWhatsappStatus(branchEntry.getWhatsAppStatus().name());
        }
        
        if (Objects.nonNull(branchEntry.getStudioId())) {
            StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
            StudioEntry studioEntry = studioManagerImpl.getById(branchEntry.getStudioId());
            branch.setStudio(ConvertToEntryUtil.convertToEntity(studioEntry, null));
        }

        return branch;
    }

    private BranchEntry convertToEntry(Branch branch) {
        BranchEntry branchEntry = new BranchEntry();

        branchEntry.setBranchId(branch.getId());
        branchEntry.setStudioId(branch.getStudio().getId());
        branchEntry.setName(branch.getName());
        branchEntry.setAddress(branch.getAddress());
        branchEntry.setCity(branch.getCity());
        branchEntry.setState(branch.getState());
        branchEntry.setPincode(branch.getPincode());
        branchEntry.setPhone(branch.getPhone());
        branchEntry.setIsActive(branch.getIsActive());
        branchEntry.setWhatsAppStatus(WhatsAppStatus.valueOf(branch.getWhatsappStatus()));

        return branchEntry;
    }

}
