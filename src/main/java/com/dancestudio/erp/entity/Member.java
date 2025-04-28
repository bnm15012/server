package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "members", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "email", "branch_id"}, name = "unique_name_email_branch")
})
public class Member extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "member_type")
    private String memberType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_member_branch_id"))
    private Branch branch;
}
