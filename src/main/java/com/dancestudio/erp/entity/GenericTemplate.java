package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "genric_template", uniqueConstraints = {
        @UniqueConstraint(name = "templateType_templateName_branch_key", columnNames = {"template_type", "template_name", "branch_id"})
})
public class GenericTemplate extends BaseEntity {

    @Column(name = "template_type", nullable = false)
    private String templateType;
    
    @Column(name = "template_name", nullable = false)
    private String templateName;

    @Column(name = "template_subject", nullable = false)
    private String templateSubject;

    @Column(name = "template_content", columnDefinition = "TEXT")
    private String templateContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_condition_branch_id"))
    private Branch branch;

}
