package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.manager.ImageManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StringResponse;
import com.dancestudio.erp.service.ImageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Component
@Setter(onMethod = @__({@Autowired}))
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageManager imageManager;

    @Override
    public ResponseEntity<StringResponse> uploadImage(String entityType, MultipartFile file) {
        StringResponse response = new StringResponse();

        try {
            String url = imageManager.uploadImage(entityType, file);
            response.setData(Collections.singletonList(url));
            response.setStatus(new StatusResponse(1, "Image uploaded successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
