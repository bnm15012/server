package com.dancestudio.erp.modules.plan;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Studio;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(
    name = "studio_plan",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_studio_plan",
            columnNames = {"studio_id", "plan_id"})
    })
public class StudioPlan extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "studio_id",
      nullable = false,
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_studio_plan_studio_id"))
  private Studio studio;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "plan_id",
      nullable = false,
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_studio_plan_plan_id"))
  private Plan plan;

  @Column(name = "custom_amount", nullable = false)
  private double customAmount;
}
