package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.response.ClientResponse;
import com.dancestudio.erp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAllClients/{studioId}/{startMonth}/{endMonth}")
    public ResponseEntity<ClientResponse> getAllClients(@PathVariable Long studioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long startMonth, @PathVariable Long endMonth) {
        return clientService.getAllClients(studioId, page, size, startMonth, endMonth);
    }

    @GetMapping("/search")
    public ResponseEntity<ClientResponse> searchClientsByName(@RequestParam String clientName) {
        return clientService.searchClientsByName(clientName);
    }
}
