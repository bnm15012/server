package com.dancestudio.erp.entity;

import com.dancestudio.erp.converter.AccessLevelConverter;
import com.dancestudio.erp.enums.AccessLevel;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_access")
public class UserAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_user_access_user"))
    private User user;

    @Convert(converter = AccessLevelConverter.class)
    @Column(name = "activity", nullable = false)
    private AccessLevel activity = AccessLevel.NONE;

    @Convert(converter = AccessLevelConverter.class)
    @Column(name = "communication", nullable = false)
    private AccessLevel communication = AccessLevel.NONE;

    @Convert(converter = AccessLevelConverter.class)
    @Column(name = "payments", nullable = false)
    private AccessLevel payments = AccessLevel.NONE;

    @Convert(converter = AccessLevelConverter.class)
    @Column(name = "expense", nullable = false)
    private AccessLevel expense = AccessLevel.NONE;

    @Convert(converter = AccessLevelConverter.class)
    @Column(name = "analysis", nullable = false)
    private AccessLevel analysis = AccessLevel.NONE;

    @Convert(converter = AccessLevelConverter.class)
    @Column(name = "reports", nullable = false)
    private AccessLevel reports = AccessLevel.NONE;

    @Convert(converter = AccessLevelConverter.class)
    @Column(name = "enquiry", nullable = false)
    private AccessLevel enquiry = AccessLevel.NONE;
}
