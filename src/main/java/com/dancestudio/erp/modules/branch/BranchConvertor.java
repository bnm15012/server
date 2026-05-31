package com.dancestudio.erp.modules.branch;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.enums.WhatsAppStatus;
import com.dancestudio.erp.manager.impl.StudioManagerImpl;
import com.dancestudio.erp.modules.studio.StudioConvertor;

import jakarta.annotation.PostConstruct;

@Component
public class BranchConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static Branch convertToEntity(BranchEntry branchEntry, Branch existingBranch) throws Exception {

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
            branch.setStudio(StudioConvertor.convertToEntity(studioEntry, null));
        }

        return branch;
    }

    public static BranchEntry convertToEntry(Branch branch) {
        return convertToEntry(branch, null);
    }

    public static BranchEntry convertToEntry(Branch branch, String[] fields) {
        BranchEntry branchEntry = new BranchEntry();

        Set<String> fieldSet = (fields == null || fields.length == 0)
                ? Collections.emptySet()
                : new HashSet<>(Arrays.asList(fields));

        boolean all = fieldSet.isEmpty();
        branchEntry.setBranchId(branch.getId());
        branchEntry.setStudioId(branch.getStudio().getId());
        setIfNeeded(all, fieldSet, "name", () -> branchEntry.setName(branch.getName()));
        setIfNeeded(all, fieldSet, "address", () -> branchEntry.setAddress(branch.getAddress()));
        setIfNeeded(all, fieldSet, "city", () -> branchEntry.setCity(branch.getCity()));
        setIfNeeded(all, fieldSet, "state", () -> branchEntry.setState(branch.getState()));
        setIfNeeded(all, fieldSet, "pincode", () -> branchEntry.setPincode(branch.getPincode()));
        setIfNeeded(all, fieldSet, "phone", () -> branchEntry.setPhone(branch.getPhone()));
        setIfNeeded(all, fieldSet, "isActive", () -> branchEntry.setIsActive(branch.getIsActive()));
        setIfNeeded(all, fieldSet, "whatsAppStatus", () -> branchEntry.setWhatsAppStatus(WhatsAppStatus.valueOf(branch.getWhatsappStatus())));
        return branchEntry;
    }

    private static void setIfNeeded(boolean all, Set<String> fields,
            String key, Runnable setter) {
        if (all || fields.contains(key)) {
            setter.run();
        }
    }
}
