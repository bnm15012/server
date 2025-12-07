package com.dancestudio.erp.modules.template.template;

import com.dancestudio.erp.entry.BaseEntry;
import com.dancestudio.erp.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
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
