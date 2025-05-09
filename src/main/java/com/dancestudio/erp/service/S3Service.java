package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.StringRequest;
import com.dancestudio.erp.response.PreSignedResponse;
import org.springframework.http.ResponseEntity;

public interface S3Service {

    ResponseEntity<PreSignedResponse> generatePresignedUrl(StringRequest request);
}
