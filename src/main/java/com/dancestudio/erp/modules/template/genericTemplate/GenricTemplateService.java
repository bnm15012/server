package com.dancestudio.erp.modules.template.genericTemplate;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.entry.GenricTemplateEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.template.template.TemplateManager;
import com.dancestudio.erp.response.StatusResponse;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class GenricTemplateService extends BaseService<GenricTemplateEntry, Long> {

    private GenricTemplateManager genericTemplateManager;

    @Autowired
    private TemplateManager templateManager;

    public ResponseEntity<BaseResponse<GenricTemplateEntry>> getAllByStudioId(Long studioId, String templateType,
            int page,
            int size) {
        BaseResponse<GenricTemplateEntry> response = new BaseResponse<GenricTemplateEntry>();
        List<GenricTemplateEntry> entries = new ArrayList<>();

        try {
            Page<GenricTemplateEntry> page2 = genericTemplateManager.getAllConditionsByStudioId(studioId, templateType,
                    --page, size);
            entries.addAll(page2.getContent());

            // Merge with TemplateEntry list (mapped to GenricTemplateEntry)
            if (templateType.equalsIgnoreCase("COMMUNICATION")) {
                List<TemplateEntry> templates = templateManager.getAllTemplates(studioId);
                entries.addAll(mapTemplatesToGenericEntries(templates, studioId));
            }

            // Set response
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Templates merged successfully",
                    StatusResponse.Type.SUCCESS, (int) page2.getTotalElements()));

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Maps a list of TemplateEntry to GenricTemplateEntry.
     */
    private List<GenricTemplateEntry> mapTemplatesToGenericEntries(List<TemplateEntry> templates, Long studioId) {
        List<GenricTemplateEntry> mappedList = new ArrayList<>();
        for (TemplateEntry t : templates) {
            GenricTemplateEntry entry = new GenricTemplateEntry();
            entry.setTemplateType("COMMUNICATION");
            entry.setTemplateName(t.getTemplateName());
            entry.setTemplateSubject(t.getSubject());
            entry.setTemplateContent(t.getTemplateBody());
            entry.setStudioId(studioId);
            mappedList.add(entry);
        }
        return mappedList;
    }

    @Override
    protected GenricTemplateEntry doAdd(GenricTemplateEntry entry) throws Exception {
        return genericTemplateManager.add(entry);
    }

    @Override
    protected GenricTemplateEntry doUpdate(Long id, GenricTemplateEntry entry) throws Exception {
        return genericTemplateManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        genericTemplateManager.delete(id);
    }

    @Override
    protected GenricTemplateEntry doGet(Long id) throws Exception {
        return genericTemplateManager.getById(id);
    }
}
