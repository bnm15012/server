package com.dancestudio.erp.modules.enquiry;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Branch;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "enquiries")
public class Enquiry extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false, length = 15)
    private String contact;

    @NotBlank
    @Size(max = 255)
    @Column(name = "purpose", nullable = false, length = 255)
    private String enquiryPurpose;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "enquiry_date", nullable = false, columnDefinition = "TIMESTAMP")
    private Date enquiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "branch_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_enquiry_branch_id")
    )
    private Branch branch;
}
