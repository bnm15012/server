package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class StudioEntry {

    private Long studioId;
    private String studioName;
    private String username;
    private String location;
    private String logo;
    private Boolean enabled;
    private String contactDetails;
}
