package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MessageStatus;
import lombok.Data;

@Data
public class MessageRecipientEntry {

    private Long id;
    private Long messageId;
    private String name;
    private String contact;
    private MessageStatus status;
    private String reason;

}
