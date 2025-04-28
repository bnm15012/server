package com.dancestudio.erp.manager.impl;

import com.cloudinary.Cloudinary;
import com.dancestudio.erp.manager.ImageManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Setter
public class ImageManagerImpl implements ImageManager {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(String entityType, MultipartFile file) throws Exception {
        if(Objects.isNull(file)) {
            throw new Exception("File not uploaded");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        Map<String, Object> uploadParams = Map.of(
                "folder", entityType,
                "public_id", uniqueFileName);

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);

        return (String) uploadResult.get("url");
    }

}
