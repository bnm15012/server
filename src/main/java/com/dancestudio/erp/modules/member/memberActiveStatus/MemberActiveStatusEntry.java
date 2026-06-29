package com.dancestudio.erp.modules.member.memberActiveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberActiveStatusEntry {

    private Long memberId;
    private List<ActivePeriod> activePeriods = new ArrayList<>();
}
