package com.dancestudio.erp.modules.member.memberActiveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents a single contiguous active period for a member.
 * A list of these periods is stored as JSON in MemberActiveStatus.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivePeriodEntry {
    private Date startDate;
    private Date endDate;
}
