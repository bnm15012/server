package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.StringRequest;
import com.dancestudio.erp.manager.S3Manager;
import com.dancestudio.erp.response.PreSignedResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.S3Service;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Setter(onMethod = @__({@Autowired}))
@Component
public class S3ServiceImpl implements S3Service {

    private S3Manager s3Manager;

    @Override
    public ResponseEntity<PreSignedResponse> generatePresignedUrl(StringRequest request) {
        PreSignedResponse response = new PreSignedResponse();
        try {
            Map<String, String> entry = s3Manager.generatePresignedUrl(request);
            response.setData(entry);
            response.setStatus(new StatusResponse(1, "Fetched pre-signed URL successufully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
