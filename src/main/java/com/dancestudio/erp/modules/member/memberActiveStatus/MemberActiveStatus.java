package com.dancestudio.erp.modules.member.memberActiveStatus;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.dancestudio.erp.modules.member.Member;

/**
 * Entity that caches a member's merged active status using a list of {@link ActivePeriod}s.
 * Saved as a One-to-Many normalized database table representation.
 */
@Entity
@Data
@Table(name = "member_active_status")
@NoArgsConstructor
public class MemberActiveStatus {

    @Id
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "memberActiveStatus", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ActivePeriod> activePeriods = new ArrayList<>();

    public MemberActiveStatus(Member member, List<ActivePeriod> activePeriods) {
        this.member = member;
        setActivePeriods(activePeriods);
    }

    public void setActivePeriods(List<ActivePeriod> periods) {
        if (this.activePeriods == null) {
            this.activePeriods = new ArrayList<>();
        } else {
            this.activePeriods.clear();
        }
        if (periods != null) {
            for (ActivePeriod p : periods) {
                p.setMemberActiveStatus(this);
                this.activePeriods.add(p);
            }
        }
    }
}
