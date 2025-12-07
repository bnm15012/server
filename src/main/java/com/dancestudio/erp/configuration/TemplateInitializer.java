package com.dancestudio.erp.configuration;

import com.dancestudio.erp.modules.template.template.Template;
import com.dancestudio.erp.modules.template.template.TemplateRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
public class TemplateInitializer implements CommandLineRunner {

    private final TemplateRepository templateRepository;
    private final ObjectMapper objectMapper;

    public TemplateInitializer(TemplateRepository templateRepository, ObjectMapper objectMapper) {
        this.templateRepository = templateRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = new ClassPathResource("templates.json").getInputStream();

        List<Template> templates = objectMapper.readValue(
                inputStream, new TypeReference<List<Template>>() {
                });

        for (Template t : templates) {
            Optional.ofNullable(templateRepository.findByName(t.getName())) // name treated as primary key
                    .map(existing -> {
                        existing.setBody(t.getBody());
                        existing.setSubject(t.getSubject());
                        existing.setTemplateType(t.getTemplateType());
                        return templateRepository.save(existing);
                    })
                    .orElseGet(() -> templateRepository.save(t));
        }
    }
}
