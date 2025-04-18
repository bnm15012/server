package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface ClientManager extends BaseManager<ClientEntry, Long> {

    Long countClientsByStudioId(Long studioId);

    Long countClientsByStudioIdAndMonth(Long studioId, Long startMonth, Long endMonth);

    List<ClientEntry> getAllClients(Long studioId, int page, int size, Long startMonth, Long endMonth) throws EntityNotFoundException;

    List<ClientEntry> searchClientsByName(String clientName) throws EntityNotFoundException;

}
