package com.dancestudio.erp.entry;

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

}
