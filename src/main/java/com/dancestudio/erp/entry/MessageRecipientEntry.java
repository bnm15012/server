package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MessageStatus;
import lombok.Data;

@Data
public class MessageRecipientEntry {

    private Long id;
    private Long messageId;
    private Long memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private MessageStatus status;
    private String reason;

}
