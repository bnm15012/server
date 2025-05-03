package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class StudioSmsUsageEntry {

    private Long id;
    private Long branchId;
    private Long month;
    private Long totalSmsSent;
    private Long quota;
}
