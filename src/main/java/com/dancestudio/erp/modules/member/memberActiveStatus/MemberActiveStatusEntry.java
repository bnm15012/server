package com.dancestudio.erp.modules.member.memberActiveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberActiveStatusEntry {

    private Long memberId;
    private Date earliestStartDate;
    private Date latestEndDate;
}

