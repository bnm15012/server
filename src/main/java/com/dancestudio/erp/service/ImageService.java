package com.dancestudio.erp.service;

import com.dancestudio.erp.response.StringResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResponseEntity<StringResponse> uploadImage(String entityType, MultipartFile file);

}
