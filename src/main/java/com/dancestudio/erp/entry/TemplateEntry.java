package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateEntry extends BaseEntry {

    private String templateName;
    private String templateBody;
    private String subject;
    private TemplateType templateType;

}
