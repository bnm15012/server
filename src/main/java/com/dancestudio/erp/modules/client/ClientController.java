package com.dancestudio.erp.modules.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;


@RestController
@RequestMapping("/clients")
public class ClientController extends BaseController<ClientEntry, Long> {

    @Autowired
    private ClientService clientService;

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<BaseResponse<ClientEntry>> getAllClients(@PathVariable Long branchId,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "0") Integer startMonth,
            @RequestParam(defaultValue = "0") Integer startYear,
            @RequestParam(defaultValue = "0") Integer endMonth,
            @RequestParam(defaultValue = "0") Integer endYear,
            @RequestParam(required = false) String searchTerm) {
        return clientService.getAllClients(branchId, page, size, startMonth, startYear, endMonth, endYear, searchTerm);
    }

    @GetMapping("/search/{branchId}")
    public ResponseEntity<BaseResponse<ClientEntry>> searchClientsByName(@PathVariable Long branchId, @RequestParam(defaultValue = "") String clientName,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return clientService.searchClientsByName(branchId, clientName, page, size);
    }

    @Override
    protected BaseService<ClientEntry, Long> getService() {
        return clientService;
    }
}
