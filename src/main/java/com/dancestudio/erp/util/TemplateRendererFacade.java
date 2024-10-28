package com.dancestudio.erp.util;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Component
@Slf4j
@Setter(onMethod = @__({@Autowired}))
public class TemplateRendererFacade {

    public ByteArrayOutputStream renderTemplate(Map<String, Object> templateKeyValueMap, String templateName, String templateBody, String renderingEngine, PDRectangle pageSize) throws Exception {
        if ("VELOCITY".equals(renderingEngine)) {
            VelocityTemplateRenderer velocityRenderer = new VelocityTemplateRenderer();
            String mergedHtml = velocityRenderer.render(templateBody, templateKeyValueMap);
//            log.info(mergedHtml);
            PdfGenerator pdfGenerator = new PdfGenerator();
            return pdfGenerator.generatePDF(mergedHtml, pageSize);
        } else {
            throw new UnsupportedOperationException("Rendering engine not supported: " + renderingEngine);
        }
    }
}
