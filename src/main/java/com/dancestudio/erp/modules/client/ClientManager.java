package com.dancestudio.erp.modules.client;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.specification.ClientSpecifications;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;

import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@Setter(onMethod = @__({ @Autowired }))
public class ClientManager extends BaseManager<Client, Long, ClientEntry> {

    private final ClientRepository clientRepository;

    protected ClientManager(ClientRepository repository) {
        super(repository, "client");
        this.clientRepository = repository;
    }

    public Page<ClientEntry> getAllClients(Long branchId, int page, int size, Integer startMonth, Integer startYear,
            Integer endMonth, Integer endYear, String searchTerm) throws EntityNotFoundException , EntityNotFoundException{
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

        Page<Client> clientPages = clientRepository.findAll(spec, pageable);

        return clientPages.map(ClientConvertor::convertToEntry);
    }

    public Page<ClientEntry> searchClientsByName(String clientName, Integer page, Integer size)
            throws EntityNotFoundException {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
        Specification<Client> spec = ClientSpecifications.searchByText(clientName);

        Page<Client> clientPages = clientRepository.findAll(spec, pageable);

        return clientPages.map(ClientConvertor::convertToEntry);
    }

    @Override
    protected Client toEntity(ClientEntry entry, Client existing)
            throws EntityNotFoundException, BeansException, Exception {
                return ClientConvertor.convertToEntity(entry, existing);

    }

    @Override
    protected ClientEntry toEntry(Client entity) throws EntityNotFoundException {
        return ClientConvertor.convertToEntry(entity);
    }

}
