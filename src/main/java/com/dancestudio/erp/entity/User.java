package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "name_email_key", columnNames = {"name", "email"})
})
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_id", nullable = true, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_studio_id"))
    private Studio studio;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    private String role;
}
