package com.dancestudio.erp.modules.template.template;

import com.dancestudio.erp.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Template extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(name = "subject")
    private String subject;

    @Column(name = "templateType", nullable = false)
    private String templateType;
}
