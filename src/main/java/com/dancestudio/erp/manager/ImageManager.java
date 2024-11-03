package com.dancestudio.erp.manager;

import com.dancestudio.erp.exception.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageManager {

    String uploadImage(String entityType, MultipartFile file) throws Exception;

}
