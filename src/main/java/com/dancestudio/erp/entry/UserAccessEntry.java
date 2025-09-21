package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.AccessLevel;
import lombok.Data;

@Data
public class UserAccessEntry {
    private AccessLevel activity;
    private AccessLevel communication;
    private AccessLevel payments;
    private AccessLevel expense;
    private AccessLevel analysis;
    private AccessLevel reports;
    private AccessLevel enquiry;
}
