package com.dancestudio.erp.modules.member.memberActiveStatus;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

import com.dancestudio.erp.modules.member.Member;

@Entity
@Data
@Table(name = "member_active_status")
public class MemberActiveStatus {

    @Id
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(name = "earliest_start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date earliestStartDate;

    @Column(name = "latest_end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date latestEndDate;
}
