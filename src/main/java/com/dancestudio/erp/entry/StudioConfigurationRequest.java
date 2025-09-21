package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.Map;

@Data
public class StudioConfigurationRequest {

    private Map<String,Boolean> configrationEntryList;

}
