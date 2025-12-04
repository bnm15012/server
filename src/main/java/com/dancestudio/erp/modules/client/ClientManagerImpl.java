package com.dancestudio.erp.modules.client;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.enums.ClientType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.specification.ClientSpecifications;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Setter(onMethod = @__({@Autowired}))
public class ClientManagerImpl implements ClientManager {

    private final ClientRepository clientRepository;

    @Autowired
    private BranchManager branchManager;

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
        return clientRepository
                .count((Specification<Client>) (root, query, cb) -> cb.equal(root.get("branch").get("id"), branchId));
    }

    @Override
    public Long countClientsByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear, Integer endMonth,
            Integer endYear) {
        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
        Specification<Client> spec = Specification
                .where(ClientSpecifications.hasBranchId(branchId))
                .and(ClientSpecifications.createdBetween(monthRange.get("start"), monthRange.get("end")));
        return clientRepository.count(spec);
    }

    @Override
    public List<ClientEntry> getAllClients(Long branchId, int page, int size, Integer startMonth, Integer startYear,
            Integer endMonth, Integer endYear, String searchTerm) throws EntityNotFoundException {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);

        Date startDate = null, endDate = null;
        if (!(startMonth.equals(0) || endMonth.equals(0) || startYear.equals(0) || endYear.equals(0))) {
            Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
            startDate = monthRange.get("start");
            endDate = monthRange.get("end");
        }

        Specification<Client> spec = Specification
                .where(ClientSpecifications.hasBranchId(branchId))
                .and(ClientSpecifications.createdBetween(startDate, endDate))
                .and(ClientSpecifications.searchByText(searchTerm));

        Page<Client> entries = clientRepository.findAll(spec, pageable);

        List<ClientEntry> clientEntries = new ArrayList<>();
        for (Client entry : entries) {
            clientEntries.add(convertToEntry(entry));
        }
        return clientEntries;
    }

    @Override
    public List<ClientEntry> searchClientsByName(String clientName, Integer page, Integer size)
            throws EntityNotFoundException {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
        Specification<Client> spec = ClientSpecifications.searchByText(clientName);

        Page<Client> clients = clientRepository.findAll(spec, pageable);
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
