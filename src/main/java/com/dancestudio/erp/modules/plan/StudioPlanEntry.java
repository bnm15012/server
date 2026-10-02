package com.dancestudio.erp.modules.plan;

import lombok.Data;

@Data
public class StudioPlanEntry {

  private Long id;
  private Long studioId;
  private Long planId;
  private Double customAmount;

  private PlanEntry plan;
}
