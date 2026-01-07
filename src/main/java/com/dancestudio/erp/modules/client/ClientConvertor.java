package com.dancestudio.erp.modules.client;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.enums.ClientType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.repository.BranchRepository;
import jakarta.annotation.PostConstruct;

@Component
public class ClientConvertor {
    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static ClientEntry convertToEntry(Client client){
        ClientEntry clientEntry = new ClientEntry();

        clientEntry.setClientId(client.getId());
        clientEntry.setGroupName(client.getGroupName());
        clientEntry.setPocName(client.getPocName());
        clientEntry.setPocPhone(client.getPocPhone());
        clientEntry.setPocEmail(client.getPocEmail());
        clientEntry.setClientType(ClientType.valueOf(client.getClientType()));
        clientEntry.setNotes(client.getNotes());
        clientEntry.setBranchId(client.getBranch().getId());

        return clientEntry;
    }

    public static Client convertToEntity(ClientEntry clientEntry, Client existingClient) throws Exception {
        Client client = (existingClient != null) ? existingClient : new Client();

        if (Objects.nonNull(clientEntry.getGroupName())) {
            client.setGroupName(clientEntry.getGroupName());
        }
        if (Objects.nonNull(clientEntry.getPocName())) {
            client.setPocName(clientEntry.getPocName());
        }
        if (Objects.nonNull(clientEntry.getPocPhone())) {
            client.setPocPhone(clientEntry.getPocPhone());
        }
        if (Objects.nonNull(clientEntry.getPocEmail())) {
            client.setPocEmail(clientEntry.getPocEmail());
        }
        if (Objects.nonNull(clientEntry.getClientType())) {
            client.setClientType(clientEntry.getClientType().name());
        }
        if (Objects.nonNull(clientEntry.getNotes())) {
            client.setNotes(clientEntry.getNotes());
        }
        if (Objects.nonNull(clientEntry.getBranchId())) {
            BranchRepository branchRepository = applicationContext.getBean(BranchRepository.class);
            Branch branch = branchRepository.findById(clientEntry.getBranchId())
                    .orElseThrow(() -> new EntityNotFoundException("Branch not found"));
            client.setBranch(branch);
        }

        return client;
    }
}
