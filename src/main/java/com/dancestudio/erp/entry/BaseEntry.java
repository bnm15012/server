package com.dancestudio.erp.entry;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BaseEntry {
    private Long id;
    private Date createdOn;
    private Date lastModifiedOn;
    private int version;
}
