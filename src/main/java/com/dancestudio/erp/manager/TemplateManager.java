package com.dancestudio.erp.manager;
import com.dancestudio.erp.entry.TemplateEntry;

import java.util.List;

public interface TemplateManager extends BaseManager<TemplateEntry, Long> {

    TemplateEntry getTemplateDetails(String templateName);

    List<TemplateEntry> getAllTemplates(Long studioId) throws Exception;


}
