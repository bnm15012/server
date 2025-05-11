package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.MessageEntry;
import com.dancestudio.erp.entry.MessageRecipientEntry;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.response.SendMessageResponse;

import java.util.List;

public interface MessageManager {

    SendMessageResponse sendMessage(SendMessageRequestEntry request);

    List<MessageEntry> getMessagesByBranchId(Long branchId, int page, int size);

    long getMessageCountByBranchId(Long branchId);

    List<MessageRecipientEntry> getMessageRecipients(Long messageId);

}
