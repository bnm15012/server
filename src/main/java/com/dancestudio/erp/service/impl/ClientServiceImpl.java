package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ClientManager;
import com.dancestudio.erp.response.ClientResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.ClientService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class ClientServiceImpl implements ClientService {

    private ClientManager clientManager;

    @Override
    public ResponseEntity<ClientResponse> addClient(ClientEntry clientEntry) {
        ClientResponse response = new ClientResponse();

        try {
            ClientEntry entry = clientManager.addClient(clientEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Client added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ClientResponse> updateClient(Long clientId, ClientEntry clientEntry) {
        ClientResponse response = new ClientResponse();

        try {
            ClientEntry entry = clientManager.updateClient(clientId, clientEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Client updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> deleteClient(Long clientId) {
        try {
            clientManager.deleteClient(clientId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ClientResponse> getClientById(Long clientId) {
        ClientResponse response = new ClientResponse();

        try {
            ClientEntry entry = clientManager.getClientById(clientId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Client retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
