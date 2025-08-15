package com.dancestudio.erp.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import com.dancestudio.erp.entry.activity.ActivityEntry;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponse extends AbstractResponse {
    private List<ActivityEntry> data;
}