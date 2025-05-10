package com.dancestudio.erp.response;


import com.dancestudio.erp.entry.MessageRecipientEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRecipientResponse extends AbstractResponse {
    private List<MessageRecipientEntry> data;
}