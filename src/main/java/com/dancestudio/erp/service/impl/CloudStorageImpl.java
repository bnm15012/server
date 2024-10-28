package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.service.CloudStorage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Setter
public class CloudStorageImpl implements CloudStorage {

//    private static final String BASE_FOLDER_NAME = "invoicing";
//    private static final String SEPARATOR = "/";
//    @Autowired
//    private BlobServiceClient blobServiceClient;
//    @Autowired
//    private BlobContainerClient blobContainerClient;
//    @Value("${azure.storage.container.name}")
//    private String containerName;
//
//    @Override
//    public String write(FileEntry fileEntry, String baseFolder) throws AzureBlobStorageException {
//        try {
//            String path = getPath(fileEntry, baseFolder);
//            BlobClient blob = blobContainerClient.getBlobClient(path);
//            blob.upload(fileEntry.getInputStream(), false);
//            return path;
//        } catch (BlobStorageException e) {
//            throw new AzureBlobStorageException(e.getServiceMessage());
//        } catch (RuntimeException e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        } catch (Exception e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        }
//    }
//
//    @Override
//    public String update(FileEntry fileEntry) throws AzureBlobStorageException {
//        try {
//            String path = fileEntry.getPath();
//            BlobClient client = blobContainerClient.getBlobClient(path);
//            client.upload(fileEntry.getInputStream(), true);
//            return path;
//        } catch (BlobStorageException e) {
//            throw new AzureBlobStorageException(e.getServiceMessage());
//        } catch (RuntimeException e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        } catch (Exception e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        }
//    }
//
//    @Override
//    public byte[] read(FileEntry fileEntry) throws AzureBlobStorageException {
//        try {
//            String path = fileEntry.getPath();
//            BlobClient client = blobContainerClient.getBlobClient(path);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            client.download(outputStream);
//            final byte[] bytes = outputStream.toByteArray();
//            return bytes;
//        } catch (BlobStorageException e) {
//            throw new AzureBlobStorageException(e.getServiceMessage());
//        } catch (RuntimeException e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        } catch (Exception e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        }
//    }
//
//    @Override
//    public List<String> listFiles(FileEntry fileEntry) throws AzureBlobStorageException {
//        try {
//            PagedIterable<BlobItem> blobList = blobContainerClient.listBlobsByHierarchy(fileEntry.getPath() + "/");
//            List<String> blobNamesList = new ArrayList<>();
//            for (BlobItem blob : blobList) {
//                blobNamesList.add(blob.getName());
//            }
//            return blobNamesList;
//        } catch (BlobStorageException e) {
//            throw new AzureBlobStorageException(e.getServiceMessage());
//        } catch (RuntimeException e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        } catch (Exception e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        }
//    }
//
//    @Override
//    public void delete(FileEntry fileEntry) throws AzureBlobStorageException {
//        try {
//            String path = fileEntry.getPath();
//            BlobClient client = blobContainerClient.getBlobClient(path);
//            client.delete();
//            log.info("Blob is deleted sucessfully.");
//        } catch (BlobStorageException e) {
//            throw new AzureBlobStorageException(e.getServiceMessage());
//        } catch (RuntimeException e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        } catch (Exception e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        }
//
//    }
//
//    @Override
//    public void createContainer() throws AzureBlobStorageException {
//        try {
//            blobServiceClient.createBlobContainer(containerName);
//            log.info("Container Created");
//        } catch (BlobStorageException e) {
//            throw new AzureBlobStorageException(e.getServiceMessage());
//        } catch (RuntimeException e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        } catch (Exception e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteContainer() throws AzureBlobStorageException {
//        try {
//            blobServiceClient.deleteBlobContainer(containerName);
//            log.info("Container Deleted");
//        } catch (BlobStorageException e) {
//            throw new AzureBlobStorageException(e.getServiceMessage());
//        } catch (RuntimeException e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        } catch (Exception e) {
//            throw new AzureBlobStorageException(e.getMessage());
//        }
//    }
//
//    public String getPath(FileEntry fileEntry, String baseFolder) {
//        if (StringUtils.isBlank(fileEntry.getFileName())) {
//            return null;
//        }
//
//        LocalDate currentDate = LocalDate.now();
//
//        String year = String.valueOf(currentDate.getYear());
//        String month = currentDate.getMonth().name();
//        String day = String.format("%02d", currentDate.getDayOfMonth());
//
//        StringBuilder fileKey = new StringBuilder();
//        fileKey.append(BASE_FOLDER_NAME)
//                .append(SEPARATOR)
//                .append(baseFolder)
//                .append(SEPARATOR)
//                .append(year)
//                .append(SEPARATOR)
//                .append(month)
//                .append(SEPARATOR)
//                .append(day)
//                .append(SEPARATOR)
//                .append(fileEntry.getFileName());
//
//        return fileKey.toString();
//    }

}
