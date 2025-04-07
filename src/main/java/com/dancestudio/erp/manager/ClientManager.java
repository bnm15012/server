package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

public interface ClientManager {

    ClientEntry addClient(ClientEntry clientEntry) throws EntityNotFoundException;

    ClientEntry updateClient(Long clientId, ClientEntry clientEntry) throws EntityNotFoundException;

    void deleteClient(Long clientId) throws EntityNotFoundException;

    ClientEntry getClientById(Long clientId) throws EntityNotFoundException;
}
