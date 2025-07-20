package com.dancestudio.erp.response;

import com.dancestudio.erp.entry.BulkUploadEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkUploadResponse extends AbstractResponse {
    private List<BulkUploadEntry> data;
}
