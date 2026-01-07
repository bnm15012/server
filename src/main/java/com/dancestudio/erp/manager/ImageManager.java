package com.dancestudio.erp.manager;

import org.springframework.web.multipart.MultipartFile;

public interface ImageManager {

    String uploadImage(String entityType, MultipartFile file) throws Exception;

}
