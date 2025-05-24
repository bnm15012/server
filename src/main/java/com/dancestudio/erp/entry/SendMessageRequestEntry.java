package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.List;

@Data
public class SendMessageRequestEntry {

    private String title;
    private String content;
    private String notificationType;
    private Long branchId;
    private Boolean sentToAll = false;
    private List<Long> memberIds;

    private String templateName;
    private String invoiceUrl;
    private Long studioId;
    private String activityType;
}
