package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class StudioSmsUsageEntry {

    private Long id;
    private Long branchId;
    private Long month;
    private Integer totalSmsSent = 0;
    private Integer quota = 0;
}
