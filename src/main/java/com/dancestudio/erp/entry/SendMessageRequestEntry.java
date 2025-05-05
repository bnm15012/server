package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.List;

@Data
public class SendMessageRequestEntry {

    private String title;
    private String content;
    private String notiticationType;
    private Long branchId;
    private Boolean sentToAll = false;
    private List<Long> memberIds;

}
