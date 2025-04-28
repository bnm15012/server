package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudioEntry;

import java.util.List;

public interface StudioManager extends BaseManager<StudioEntry, Long> {

    List<StudioEntry> getAllStudios() throws Exception;
}
