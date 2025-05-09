package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StringRequest;

import java.util.Map;

public interface S3Manager {

    Map<String,String> generatePresignedUrl(StringRequest request);

}
