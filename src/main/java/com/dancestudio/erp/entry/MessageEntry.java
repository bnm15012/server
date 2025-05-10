package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.Date;

@Data
public class MessageEntry {

    private Long id;
    private String title;
    private String content;
    private String notificationType;
    private Date sentDate;
    private Long branchId;
    private Boolean sentToAll;

}
