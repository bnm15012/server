package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.response.ClientResponse;
import com.dancestudio.erp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<ClientResponse> addClient(@RequestBody ClientEntry clientEntry) {
        return clientService.addClient(clientEntry);
    }

    @PutMapping("/update/{clientId}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long clientId, @RequestBody ClientEntry clientEntry) {
        return clientService.updateClient(clientId, clientEntry);
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        return clientService.deleteClient(clientId);
    }

    @GetMapping("/get/{clientId}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

}
