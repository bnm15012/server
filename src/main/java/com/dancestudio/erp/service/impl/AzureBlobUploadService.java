//package com.dancestudio.erp.service.impl;
//
//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobClientBuilder;
//import com.azure.storage.blob.models.BlobHttpHeaders;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//@Service
//public class AzureBlobUploadService {
//
//    @Value("${azure.storage.container.name}")
//    private String containerName;
//
//    @Value("${azure.storage.connection.string}")
//    private String connectionString;
//
//    public String uploadImageToBlob(Long studioId, String folder, String entityName, byte[] imageBytes) {
//        String sanitizedEntityName = entityName.replaceAll("[^a-zA-Z0-9]", "_");
//        String blobName = String.format("%d/%s/%s.jpg", studioId, folder, sanitizedEntityName);
//
//        BlobClient blobClient = new BlobClientBuilder()
//                .connectionString(connectionString)
//                .containerName(containerName)
//                .blobName(blobName)
//                .buildClient();
//
//        InputStream inputStream = new ByteArrayInputStream(imageBytes);
//        blobClient.upload(inputStream, imageBytes.length, true);
//        blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType("image/jpeg"));
//
//        return blobClient.getBlobUrl();
//    }
//
//    public byte[] getImageInBytes(String imageUrl) {
//
//        String[] urlParts = imageUrl.split("/");
//        String blobName = urlParts[urlParts.length - 1];
//
//        BlobClient blobClient = new BlobClientBuilder()
//                .connectionString(connectionString)
//                .containerName(containerName)
//                .blobName(blobName)
//                .buildClient();
//
//        return blobClient.downloadContent().toBytes();
//    }
//}
