package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.response.SendMessageResponse;

public interface MessageManager {

    SendMessageResponse sendMessage(SendMessageRequestEntry request);

}
