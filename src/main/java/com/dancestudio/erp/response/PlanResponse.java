package com.dancestudio.erp.response;


import com.dancestudio.erp.entry.PlanEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponse extends AbstractResponse {
    private List<PlanEntry> data;
}