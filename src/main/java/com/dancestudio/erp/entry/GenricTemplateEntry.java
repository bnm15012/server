package com.dancestudio.erp.entry;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenricTemplateEntry {
    private Long id;
    private String templateType;
    private String templateName;
    private String templateSubject;
    private String templateContent;
    private Long studioId;
}
