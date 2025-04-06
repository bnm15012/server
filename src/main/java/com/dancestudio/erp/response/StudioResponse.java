package com.dancestudio.erp.response;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.SubscriptionPlanEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioResponse extends AbstractResponse {

    private List<StudioEntry> data;
    private SubscriptionPlanEntry subscriptionPlanEntry;

}