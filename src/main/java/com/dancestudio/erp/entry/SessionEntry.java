package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class SessionEntry {

    private boolean success;
    private String message;
    private Object data;
}
