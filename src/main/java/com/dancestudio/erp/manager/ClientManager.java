package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface ClientManager {

    ClientEntry addClient(ClientEntry clientEntry) throws EntityNotFoundException;

    ClientEntry updateClient(Long clientId, ClientEntry clientEntry) throws EntityNotFoundException;

    void deleteClient(Long clientId) throws EntityNotFoundException;

    ClientEntry getClientById(Long clientId) throws EntityNotFoundException;

    Long countClientsByStudioId(Long studioId);

    Long countClientsByStudioIdAndMonth(Long studioId, Long startMonth, Long endMonth);

    List<ClientEntry> getAllClients(Long studioId, int page, int size, Long startMonth, Long endMonth) throws EntityNotFoundException;

    List<ClientEntry> searchClientsByName(String clientName) throws EntityNotFoundException;

}
