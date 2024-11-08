package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Template extends BaseEntity {

    private static final long serialVersionUID = -1700105192677945854L;
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    @Column(name = "name")
    private String name;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;

}