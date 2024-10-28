package com.dancestudio.erp.util;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Setter(onMethod = @__({@Autowired}))
@Component
public class TemplateUtil {
//    private TemplateRendererFacade templateRendererFacade;
//    private TemplateManager templateManager;
//
//    public ByteArrayOutputStream renderTemplate(Map<String, Object> templateKeyValueMap, String templateName, PDRectangle pageSize) throws Exception {
//        try {
//            TemplateEntry template = templateManager.getTemplateDetails(templateName);
//            if (template == null) {
//                log.error("No template found with name: {}", templateName);
//            }
//            return templateRendererFacade.renderTemplate(templateKeyValueMap, templateName, template.getTemplateBody(), "VELOCITY", pageSize);
//        } catch (Exception e) {
//            log.error("Error rendering template: " + templateName, e);
//            throw e;
//        }
//    }

}
