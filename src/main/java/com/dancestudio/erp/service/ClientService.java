package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.response.ClientResponse;
import org.springframework.http.ResponseEntity;

public interface ClientService {

    ResponseEntity<ClientResponse> addClient(ClientEntry clientEntry);

    ResponseEntity<ClientResponse> updateClient(Long clientId, ClientEntry clientEntry);

    ResponseEntity<Void> deleteClient(Long clientId);

    ResponseEntity<ClientResponse> getClientById(Long clientId);

    ResponseEntity<ClientResponse> getAllClients(Long studioId, int page, int size, Long startMonth, Long endMonth);

    ResponseEntity<ClientResponse> searchClientsByName(String clientName);

}
