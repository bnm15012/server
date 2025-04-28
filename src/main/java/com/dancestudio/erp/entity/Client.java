package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(name = "groupname_email_key", columnNames = {"group_name", "poc_email"})
})
public class Client extends BaseEntity {

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "poc_name", nullable = false)
    private String pocName;

    @Column(name = "poc_phone", nullable = false, length = 10)
    private String pocPhone;

    @Column(name = "poc_email")
    private String pocEmail;

    @Column(name = "client_type", nullable = false)
    private String clientType;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_client_branch_id"))
    private Branch branch;

}
