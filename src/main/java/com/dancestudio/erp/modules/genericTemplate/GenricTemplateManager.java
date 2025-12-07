package com.dancestudio.erp.modules.genericTemplate;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.entry.GenricTemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.repository.GenricTemplateRepository;
import lombok.Setter;

import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter(onMethod = @__({ @Autowired }))
@Transactional
public class GenricTemplateManager extends BaseManager<GenericTemplate, Long, GenricTemplateEntry> {

    private final GenricTemplateRepository conditionsRepository;

    public GenricTemplateManager(GenricTemplateRepository conditionsRepository) {
        super(conditionsRepository, "GenericTemplate");
        this.conditionsRepository = conditionsRepository;
    }

    public Page<GenricTemplateEntry> getAllConditionsByStudioId(Long studioId, String templateType, Integer page,
            Integer size) throws Exception {
        Pageable pageable = size == -1 ? Pageable.unpaged()
                : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));
        Page<GenericTemplate> conditionsPage = conditionsRepository.findByStudioIdAndFilters(studioId, templateType,
                pageable);
        return conditionsPage.map(GenericTemplateConvertor::convertToEntry);
    }

    @Override
    protected GenericTemplate toEntity(GenricTemplateEntry entry, GenericTemplate existing)
            throws EntityNotFoundException, BeansException, Exception {
        return GenericTemplateConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected GenricTemplateEntry toEntry(GenericTemplate entity) throws EntityNotFoundException {
        return GenericTemplateConvertor.convertToEntry(entity);
    }
}
