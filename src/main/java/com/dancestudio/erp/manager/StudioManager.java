package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudioEntry;

import java.util.List;

public interface StudioManager extends BaseManagerInt<StudioEntry, Long> {

    List<StudioEntry> getAllStudios() throws Exception;
}
