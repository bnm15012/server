package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Client;
import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.enums.ClientType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ClientManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.ClientRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Setter
public class ClientManagerImpl implements ClientManager {

    private final ClientRepository clientRepository;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    public ClientManagerImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientEntry addClient(ClientEntry clientEntry) throws EntityNotFoundException {

        if (Objects.isNull(clientEntry.getStudioId())) {
            throw new EntityNotFoundException("StudioId cannot be null");
        }

        studioManager.getStudioById(clientEntry.getStudioId());
        Client client = convertToEntity(clientEntry, null);
        return convertToEntry(clientRepository.save(client));
    }

    @Override
    public ClientEntry updateClient(Long clientId, ClientEntry clientEntry) throws EntityNotFoundException {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        Client updatedClient = convertToEntity(clientEntry, existingClient);
        return convertToEntry(clientRepository.save(updatedClient));
    }

    @Override
    public void deleteClient(Long clientId) throws EntityNotFoundException {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        clientRepository.deleteById(clientId);
    }

    @Override
    public ClientEntry getClientById(Long clientId) throws EntityNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        return convertToEntry(client);
    }

    public ClientEntry convertToEntry(Client client) throws EntityNotFoundException {
        ClientEntry clientEntry = new ClientEntry();

        clientEntry.setId(client.getId());
        clientEntry.setGroupName(client.getGroupName());
        clientEntry.setPocName(client.getPocName());
        clientEntry.setPocPhone(client.getPocPhone());
        clientEntry.setPocEmail(client.getPocEmail());
        clientEntry.setClientType(ClientType.valueOf(client.getClientType()));
        clientEntry.setNotes(client.getNotes());
        clientEntry.setStudioId(client.getStudio().getId());

        return clientEntry;
    }

    private Client convertToEntity(ClientEntry clientEntry, Client existingClient) throws EntityNotFoundException {
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
        if (Objects.nonNull(clientEntry.getStudioId())) {
            StudioEntry studioEntry = studioManager.getStudioById(clientEntry.getStudioId());
            client.setStudio(ConvertToEntryUtil.convertToEntity(studioEntry, null));
        }

        return client;
    }
}
