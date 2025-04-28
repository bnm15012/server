package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.List;

@Data
public class StudioConfigurationRequest {

    private List<StudioConfigurationEntry> configrationEntryList;

}
