package com.dancestudio.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.response.ClientResponse;
import com.dancestudio.erp.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController extends BaseController<ClientEntry, ClientResponse, Long> {

    @Autowired
    private ClientService clientService;

    @Override
    public ResponseEntity<ClientResponse> add(@RequestBody ClientEntry clientEntry) {
        return clientService.add(clientEntry);
    }

    @Override
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @RequestBody ClientEntry clientEntry) {
        return clientService.update(id, clientEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return clientService.delete(id);
    }

    @Override
    public ResponseEntity<ClientResponse> get(@PathVariable Long id) {
        return clientService.get(id);
    }

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<ClientResponse> getAllClients(@PathVariable Long branchId,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "0") Integer startMonth,
            @RequestParam(defaultValue = "0") Integer startYear,
            @RequestParam(defaultValue = "0") Integer endMonth,
            @RequestParam(defaultValue = "0") Integer endYear,
            @RequestParam(required = false) String searchTerm) {
        return clientService.getAllClients(branchId, page, size, startMonth, startYear, endMonth, endYear, searchTerm);
    }

    @GetMapping("/search")
    public ResponseEntity<ClientResponse> searchClientsByName(@RequestParam(defaultValue = "") String clientName,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return clientService.searchClientsByName(clientName, page, size);
    }
}
