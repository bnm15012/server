package com.dancestudio.erp.modules.client;

import com.dancestudio.erp.response.ClientResponse;
import com.dancestudio.erp.service.BaseService;

import org.springframework.http.ResponseEntity;

public interface ClientService extends BaseService<ClientEntry, ClientResponse, Long> {

    ResponseEntity<ClientResponse> getAllClients(Long branchId, Integer page, Integer size, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String searchTerm);

    ResponseEntity<ClientResponse> searchClientsByName(String clientName, Integer page, Integer size);

}