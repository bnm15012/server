package com.dancestudio.erp.util;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.nio.file.*;
import java.net.URLConnection;
import java.util.Objects;

@Slf4j
public class TempFileUtil {

    public static String saveTempFile(byte[] fileBytes, String originalName, String contentType) throws IOException {
        String prefix = originalName.replaceAll("[^a-zA-Z0-9]", "_"); // sanitize name
        String suffix = contentType != null && contentType.contains("/")
                ? "." + contentType.substring(contentType.indexOf("/") + 1)
                : ".tmp";

        Path tempFile = Files.createTempFile(prefix + "_", suffix);
        Files.write(tempFile, fileBytes);
        return tempFile.toAbsolutePath().toString();
    }

    public static void removeTempFile(String pathStr) {
        try {
            if (pathStr != null) {
                Path path = Paths.get(pathStr);
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            }
        } catch (IOException e) {
            log.error("Failed to delete temp file: {} -> {}", pathStr, e.getMessage(), e);
        }
    }

    public static FileData getFile(String pathStr) throws IOException {
        if (pathStr == null) {
            return new FileData(null, null, null);
        }
        Objects.requireNonNull(pathStr, "Path cannot be null");
        Path path = Paths.get(pathStr);

        if (!Files.exists(path)) {
            System.err.println("File does not exist: " + path);
            return new FileData(null, null, null);
        }

        byte[] fileBytes = Files.readAllBytes(path);
        String originalName = path.getFileName().toString();

        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = URLConnection.guessContentTypeFromName(originalName);
        }
        if (contentType == null) {
            contentType = "application/octet-stream"; // fallback
        }

        return new FileData(fileBytes, originalName, contentType);
    }

    /**
     * DTO for file info.
     */
    public static class FileData {
        private final byte[] fileBytes;
        private final String originalName;
        private final String contentType;

        public FileData(byte[] fileBytes, String originalName, String contentType) {
            this.fileBytes = fileBytes;
            this.originalName = originalName;
            this.contentType = contentType;
        }

        public byte[] getFileBytes() {
            return fileBytes;
        }

        public String getOriginalName() {
            return originalName;
        }

        public String getContentType() {
            return contentType;
        }
    }
}
