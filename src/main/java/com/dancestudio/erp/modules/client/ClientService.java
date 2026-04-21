package com.dancestudio.erp.modules.client;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.response.StatusResponse;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class ClientService extends BaseService<ClientEntry, Long> {

    private ClientManager clientManager;

    public ResponseEntity<BaseResponse<ClientEntry>> getAllClients(Long branchId, Integer page, Integer size, Integer startMonth,
            Integer startYear, Integer endMonth, Integer endYear, String searchTerm) {
        BaseResponse<ClientEntry> response = new BaseResponse<>();

        try {
            Page<ClientEntry> entries = clientManager.getAllClients(branchId, --page, size, startMonth, startYear,
                    endMonth, endYear, searchTerm);

            response.setData(entries.getContent());
            response.setStatus(new StatusResponse(1, "Clients retrieved successfully", StatusResponse.Type.SUCCESS,
                    entries.getTotalElements()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<BaseResponse<ClientEntry>> searchClientsByName(Long branchId, String clientName, Integer page, Integer size) {
        BaseResponse<ClientEntry> response = new BaseResponse<>();
        try {
            Page<ClientEntry> clients = clientManager.searchClientsByName(branchId, clientName, --page, size);
            response.setData(clients.getContent());
            response.setStatus(new StatusResponse(1, "Client retrieved successfully", StatusResponse.Type.SUCCESS, (int) clients.getTotalElements()));
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
    protected ClientEntry doAdd(ClientEntry entry) throws Exception {
        return clientManager.add(entry);
    }

    @Override
    protected ClientEntry doUpdate(Long id, ClientEntry entry) throws Exception {
        return clientManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        clientManager.delete(id);
    }

    @Override
    protected ClientEntry doGet(Long id) throws Exception {
        return clientManager.getById(id);
    }
}
