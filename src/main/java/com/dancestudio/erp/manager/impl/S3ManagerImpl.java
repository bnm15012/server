package com.dancestudio.erp.manager.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.dancestudio.erp.entry.StringRequest;
import com.dancestudio.erp.manager.S3Manager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Setter
public class S3ManagerImpl implements S3Manager {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Autowired private AmazonS3 amazonS3;

    public Map<String,String> generatePresignedUrl(StringRequest inputRequest) {

        String fileName = inputRequest.getData().get("fileName");
        String contentType = inputRequest.getData().get("contentType");

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 86400); // 1 day
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        request.setContentType(contentType);
        request.addRequestParameter("x-amz-acl", "public-read"); // <-- This line makes it public

        URL url = amazonS3.generatePresignedUrl(request);

        Map<String, String> response = new HashMap<>();
        response.put("uploadUrl", url.toString());
        response.put("fileUrl", "https://" + bucketName + ".s3.amazonaws.com/" + fileName);

        return response;
    }
}
