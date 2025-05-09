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
import java.util.List;

@Setter(onMethod = @__({@Autowired}))
@Component
public class ClientServiceImpl implements ClientService {

    private ClientManager clientManager;

    @Override
    public ResponseEntity<ClientResponse> add(ClientEntry clientEntry) {
        ClientResponse response = new ClientResponse();

        try {
            ClientEntry entry = clientManager.add(clientEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Client added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ClientResponse> update(Long clientId, ClientEntry clientEntry) {
        ClientResponse response = new ClientResponse();

        try {
            ClientEntry entry = clientManager.update(clientId, clientEntry);

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
    public ResponseEntity<Void> delete(Long clientId) {
        try {
            clientManager.delete(clientId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ClientResponse> get(Long clientId) {
        ClientResponse response = new ClientResponse();

        try {
            ClientEntry entry = clientManager.getById(clientId);

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

    @Override
    public ResponseEntity<ClientResponse> getAllClients(Long branchId, Integer page, Integer size, Integer startMonth,Integer startYear,  Integer endMonth, Integer endYear, String searchTerm) {
        ClientResponse response = new ClientResponse();

        try {
            List<ClientEntry> entries = clientManager.getAllClients(branchId, page, size, startMonth, startYear, endMonth, endYear, searchTerm);

            long clientCount = (startMonth.equals(0) || endMonth.equals(0) || startYear.equals(0) || endYear.equals(0))
                    ? clientManager.countClientsByBranchId(branchId)
                    : clientManager.countClientsByBranchIdAndMonth(branchId, startMonth, startYear, endMonth, endYear);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Clients retrieved successfully", StatusResponse.Type.SUCCESS, (int) clientCount));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ClientResponse> searchClientsByName(String clientName, Integer page, Integer size) {
        ClientResponse response = new ClientResponse();
        try {
            List<ClientEntry> clients = clientManager.searchClientsByName(clientName, page, size);
            response.setData(clients);
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
