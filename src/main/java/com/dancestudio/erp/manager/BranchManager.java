package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BranchEntry;

import java.util.List;

public interface BranchManager extends BaseManagerInt<BranchEntry, Long> {

    List<BranchEntry> findByStudioId(Long studioId) throws Exception;

    BranchEntry enableDisableBranch(Long branchId, boolean flag) throws Exception;
}
