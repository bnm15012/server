package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.modules.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "member_additional_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentEnrollmentData extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    @Column(name = "additional_data", columnDefinition = "json")
    private String additionalData;
}
