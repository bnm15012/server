package com.dancestudio.erp.entry;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConditionsEntry {
    private Long id;
    private String entityType;
    private String description;
    private String activityType;
    private String templateName;
    private Long branchId;
}
