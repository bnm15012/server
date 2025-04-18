package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.ClientType;
import lombok.Data;

@Data
public class ClientEntry {

    private Long clientId;
    private String groupName;
    private String pocName;
    private String pocPhone;
    private String pocEmail;
    private ClientType clientType;
    private String notes;

    private Long studioId;

}
