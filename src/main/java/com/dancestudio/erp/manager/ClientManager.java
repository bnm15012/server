package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface ClientManager extends BaseManager<ClientEntry, Long> {

    Long countClientsByBranchId(Long branchId);

    Long countClientsByBranchIdAndMonth(Long branchId, Long startMonth, Long endMonth);

    List<ClientEntry> getAllClients(Long branchId, int page, int size, Long startMonth, Long endMonth, String searchTerm) throws EntityNotFoundException;

    List<ClientEntry> searchClientsByName(String clientName) throws EntityNotFoundException;

}
