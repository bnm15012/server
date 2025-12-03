package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface ClientManager extends BaseManagerInt<ClientEntry, Long> {

    Long countClientsByBranchId(Long branchId);

    Long countClientsByBranchIdAndMonth(Long branchId, Integer startMonth,Integer startYear,  Integer endMonth, Integer endYear);

    List<ClientEntry> getAllClients(Long branchId, int page, int size, Integer startMonth,Integer startYear,  Integer endMonth, Integer endYear, String searchTerm) throws EntityNotFoundException;

    List<ClientEntry> searchClientsByName(String clientName, Integer page, Integer size) throws EntityNotFoundException;

}
