package com.dancestudio.erp.response;

import com.dancestudio.erp.entry.GenricTemplateEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericTemplateResponse extends AbstractResponse {

    private List<GenricTemplateEntry> data;
}
