package com.dancestudio.erp.modules.template.genericTemplate;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Studio;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "generic_template", uniqueConstraints = {
        @UniqueConstraint(name = "templateType_templateName_studio_key", columnNames = {"template_type", "template_name", "studio_id"})
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
    @JoinColumn(name = "studio_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_condition_studio_id"))
    private Studio studio;

}
