package com.dancestudio.erp.entry;

import lombok.Data;
import java.util.List;

@Data
public class StudioEntry {

    private Long studioId;
    private String studioName;
    private String userName;
    private String email;
    private String location;
    private String logo;
    private String passcode;
    private Boolean enabled;
    private String contactDetails;
    private StudioConfigurationRequest configuration;
    private String gstNumber;
    private Boolean amcEnabled;

    private SubscriptionEntry subscriptionEntry;
    private List<BranchEntry> branchList;
}
