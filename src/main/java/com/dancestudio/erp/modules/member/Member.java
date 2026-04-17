package com.dancestudio.erp.modules.member;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Date;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Branch;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "members", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "email", "member_type", "branch_id"}, name = "unique_name_email_membertype_branch")
})
public class Member extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "address", length = 1024)
    private String address;

    @Column(name = "emergency_contact_number", nullable = false, length = 10)
    private String emergencyContactNumber;

    @Column(name = "member_type")
    private String memberType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_member_branch_id"))
    private Branch branch;
}
