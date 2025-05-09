package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.response.ClientResponse;
import org.springframework.http.ResponseEntity;

public interface ClientService extends BaseService<ClientEntry, ClientResponse, Long> {

    ResponseEntity<ClientResponse> getAllClients(Long branchId, Integer page, Integer size, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String searchTerm);

    ResponseEntity<ClientResponse> searchClientsByName(String clientName, Integer page, Integer size);

}