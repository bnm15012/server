package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.List;

import com.dancestudio.erp.enums.MemberType;

@Data
public class SendMessageRequestEntry {

    private String title;
    private String content;
    private String notificationType;
    private Long branchId;
    private MemberType memberType;
    private List<Long> memberIds;
    private List<Long> clientIds;

    private String templateName; // should remove
    private String invoiceUrl; // should remove
    private Long studioId;
    private String activityType;
}
