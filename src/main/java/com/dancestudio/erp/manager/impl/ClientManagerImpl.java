package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Client;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.enums.ClientType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.ClientManager;
import com.dancestudio.erp.repository.ClientRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Setter
public class ClientManagerImpl implements ClientManager {

    private final ClientRepository clientRepository;

    @Autowired
    private BranchManager branchManager;

    @Autowired
    public ClientManagerImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientEntry add(ClientEntry clientEntry) throws Exception {

        if (Objects.isNull(clientEntry.getBranchId())) {
            throw new EntityNotFoundException("BranchId cannot be null");
        }

        branchManager.getById(clientEntry.getBranchId());
        Client client = convertToEntity(clientEntry, null);
        return convertToEntry(clientRepository.save(client));
    }

    @Override
    public ClientEntry update(Long clientId, ClientEntry clientEntry) throws Exception {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        Client updatedClient = convertToEntity(clientEntry, existingClient);
        return convertToEntry(clientRepository.save(updatedClient));
    }

    @Override
    public void delete(Long clientId) throws EntityNotFoundException {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        clientRepository.deleteById(clientId);
    }

    @Override
    public ClientEntry getById(Long clientId) throws EntityNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        return convertToEntry(client);
    }

    @Override
    public Long countClientsByBranchId(Long branchId) {
        return clientRepository.countClientsByBranchId(branchId);
    }

    @Override
    public Long countClientsByBranchIdAndMonth(Long branchId, Long startMonth, Long endMonth) {
        return clientRepository.countClientsByBranchIdAndMonthLong(branchId, startMonth, endMonth);
    }

    @Override
    public List<ClientEntry> getAllClients(Long branchId, int page, int size, Long startMonth, Long endMonth, String searchTerm) throws EntityNotFoundException {
        Page<Client> entries;
        Pageable pageable = PageRequest.of(page, size);
        if (startMonth.equals(0L) || endMonth.equals(0L)) {
            entries = clientRepository.findClientsByBranchId(branchId, pageable, searchTerm);
        } else {
            entries = clientRepository.findAllByBranchId(branchId, startMonth, endMonth, pageable, searchTerm);
        }

        List<ClientEntry> clientEntries = new ArrayList<>();
        for (Client entry : entries) {
            ClientEntry clientEntry = convertToEntry(entry);
            clientEntries.add(clientEntry);
        }

        return clientEntries;
    }

    @Override
    public List<ClientEntry> searchClientsByName(String clientName) throws EntityNotFoundException {
        List<Client> clients = clientRepository.findByGroupNameContainingIgnoreCase(clientName);
        List<ClientEntry> clientEntries = new ArrayList<>();
        for (Client client : clients) {
            clientEntries.add(convertToEntry(client));
        }
        return clientEntries;
    }

    public ClientEntry convertToEntry(Client client) throws EntityNotFoundException {
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

    private Client convertToEntity(ClientEntry clientEntry, Client existingClient) throws Exception {
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
            BranchEntry branchEntry = branchManager.getById(clientEntry.getBranchId());
            client.setBranch(ConvertToEntryUtil.convertToEntity(branchEntry, null));
        }

        return client;
    }
}
