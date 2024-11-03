package com.dancestudio.erp.controller;

import com.dancestudio.erp.response.StringResponse;
import com.dancestudio.erp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload/{entityType}")
    public ResponseEntity<StringResponse> uploadImage(@PathVariable("entityType") String entityType,
                                                      @RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(entityType, file);
    }
}
