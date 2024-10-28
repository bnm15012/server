package com.dancestudio.erp.util;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

public class VelocityTemplateRenderer {

    public String render(String templateBody, Map<String, Object> templateKeyValueMap) {
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "string");
        properties.setProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        velocityEngine.init(properties);

        VelocityContext velocityContext = new VelocityContext();
        for (Map.Entry<String, Object> entry : templateKeyValueMap.entrySet()) {
            velocityContext.put(entry.getKey(), entry.getValue());
        }

        StringWriter stringWriter = new StringWriter();
        velocityEngine.evaluate(velocityContext, stringWriter, "template", templateBody);

        return stringWriter.toString();
    }
}
