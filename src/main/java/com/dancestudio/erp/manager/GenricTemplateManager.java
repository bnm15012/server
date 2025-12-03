package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.GenricTemplateEntry;

import java.util.List;

public interface GenricTemplateManager extends BaseManagerInt<GenricTemplateEntry, Long> {

    List<GenricTemplateEntry> getAllConditionsByStudioId(Long studioId, String templateType, Integer page, Integer size) throws Exception;

    long countByFilters(Long studioId, String templateType) throws Exception;
}
