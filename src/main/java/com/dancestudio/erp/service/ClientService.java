package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.response.ClientResponse;
import org.springframework.http.ResponseEntity;

public interface ClientService extends BaseService<ClientEntry, ClientResponse, Long> {

    ResponseEntity<ClientResponse> getAllClients(Long studioId, int page, int size, Long startMonth, Long endMonth);

    ResponseEntity<ClientResponse> searchClientsByName(String clientName);

}