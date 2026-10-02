package com.dancestudio.erp.modules.plan;

import com.dancestudio.erp.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class StudioPlanConvertor {

  public static StudioPlanEntry convertToEntry(StudioPlan studioPlan)
      throws EntityNotFoundException {
    StudioPlanEntry entry = new StudioPlanEntry();
    entry.setId(studioPlan.getId());
    entry.setStudioId(studioPlan.getStudio().getId());
    entry.setPlanId(studioPlan.getPlan().getId());
    entry.setCustomAmount(studioPlan.getCustomAmount());

    PlanEntry planEntry = PlanConvertor.convertToEntry(studioPlan.getPlan());
    planEntry.setAmount(studioPlan.getCustomAmount());
    entry.setPlan(planEntry);

    return entry;
  }
}
