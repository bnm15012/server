package com.dancestudio.erp.modules.genericTemplate;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.entry.GenricTemplateEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genericTemplate")
public class GenricTemplateController extends BaseController<GenricTemplateEntry, Long> {

    @Autowired
    private GenricTemplateService genericTemplateService;

    @GetMapping("/getAll/{studioId}")
    public ResponseEntity<BaseResponse<GenricTemplateEntry>> getAllConditions(
            @PathVariable Long studioId,
            @RequestParam(required = false, name = "searchTerm") String templateType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return genericTemplateService.getAllByStudioId(studioId, templateType, page, size);
    }

    @Override
    protected BaseService<GenricTemplateEntry, Long> getService() {
        return genericTemplateService;
    }
}
