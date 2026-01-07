package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.BulkUpload;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.BulkUploadEntry;
import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.BulkUploadManager;
import com.dancestudio.erp.modules.member.instructor.InstructorEntry;
import com.dancestudio.erp.modules.member.instructor.InstructorManager;
import com.dancestudio.erp.modules.member.student.StudentEntry;
import com.dancestudio.erp.modules.member.student.StudentManager;
import com.dancestudio.erp.repository.BulkUploadRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@Setter(onMethod = @__({@Autowired}))
@Slf4j
public class BulkUploadManagerImpl implements BulkUploadManager {

    private StudentManager studentManager;
    private InstructorManager instructorManager;
    private BranchManager branchManager;

    private final BulkUploadRepository bulkUploadRepository;

    public BulkUploadManagerImpl(BulkUploadRepository bulkUploadRepository) {
        this.bulkUploadRepository = bulkUploadRepository;
    }

    @Override
    public BulkUploadEntry add(BulkUploadEntry bulkUploadEntry) throws Exception {

        if (Objects.isNull(bulkUploadEntry.getBranchEntry().getBranchId())) {
            throw new EntityNotFoundException("BranchId cannot be null");
        }

        branchManager.getById(bulkUploadEntry.getBranchEntry().getBranchId());
        BulkUpload bulkUpload = convertToEntity(bulkUploadEntry, null);
        return convertToEntry(bulkUploadRepository.save(bulkUpload));
    }

    @Override
    public BulkUploadEntry update(Long jobId, BulkUploadEntry bulkUploadEntry) throws Exception {
        BulkUpload existingBulkUpload = bulkUploadRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));

        BulkUpload updatedBulkUpload = convertToEntity(bulkUploadEntry, existingBulkUpload);
        return convertToEntry(bulkUploadRepository.save(updatedBulkUpload));
    }

    @Override
    public void delete(Long clientId) throws EntityNotFoundException {
        bulkUploadRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));

        bulkUploadRepository.deleteById(clientId);
    }

    @Override
    public BulkUploadEntry getById(Long jobId) throws EntityNotFoundException {
        BulkUpload bulkUpload = bulkUploadRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));

        return convertToEntry(bulkUpload);
    }

    @Override
    public BulkUploadEntry processBulkUpload(BulkUploadEntry jobEntry, Long branchId, String entityType, String fileUrl) {
        HttpURLConnection connection = null;
        try {
            if (!MemberType.STUDENT.name().equalsIgnoreCase(entityType) && !MemberType.INSTRUCTOR.name().equalsIgnoreCase(entityType)) {
                throw new IllegalArgumentException("Invalid entity type. Must be 'STUDENT' or 'INSTRUCTOR'");
            }

            String encodedUrl = fileUrl.replace(" ", "%20");
            URL url = new URL(encodedUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to download file from URL. Response code: " + responseCode);
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(connection.getInputStream()))) {
                List<Map<String, String>> records = parseCSV(reader);
                if (records.isEmpty()) {
                    throw new IllegalArgumentException("CSV file is empty");
                }

                jobEntry = processRecords(branchId, entityType, records, jobEntry);
                return jobEntry;
            } catch (IOException | CsvValidationException e) {
                throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (Exception e) {
            if (connection != null) {
                connection.disconnect();
            }
            log.error("Error processing bulk upload: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing bulk upload: " + e.getMessage(), e);
        }
    }

    @Override
    public BulkUploadEntry updateBulkUploadStatus(Long id, String status, String errorMessage) {
        BulkUpload job = bulkUploadRepository.findById(id).get();
        job.setStatus(status);

        if (errorMessage != null) {
            job.setErrorMessage(errorMessage);
        }
        if ("COMPLETED".equals(status) || "FAILED".equals(status)) {
            job.setCompletedAt(DateUtil.getCurrentDateUTC());
        }
        try {
            return convertToEntry(bulkUploadRepository.save(job));
        } catch (Exception e) {
            log.error("Error updating bulk upload status: {}", e.getMessage(), e);
            return null;
        }
    }

    @SneakyThrows
    @Override
    public List<BulkUploadEntry> getAllJobs(Long branchId, Integer page, Integer size) {
        Page<BulkUpload> entries;
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size, Sort.by("lastModifiedOn").descending());

        entries = bulkUploadRepository.findByBranchId(branchId, pageable);

        List<BulkUploadEntry> bulkUploadEntries = new ArrayList<>();
        for (BulkUpload entry : entries) {
            BulkUploadEntry bulkUploadEntry = convertToEntry(entry);
            bulkUploadEntries.add(bulkUploadEntry);
        }

        return bulkUploadEntries;
    }

    @Override
    public Long countJobsByBranchId(Long branchId) {
        return bulkUploadRepository.countByBranchId(branchId);
    }


    private List<Map<String, String>> parseCSV(CSVReader csvReader) throws IOException, CsvValidationException {
        List<Map<String, String>> records = new ArrayList<>();
        String[] headers = csvReader.readNext();

        if (headers == null)
            return records;

        for (int i = 0; i < headers.length; i++) {
            headers[i] = headers[i].trim().toLowerCase();
        }

        String[] values;
        while ((values = csvReader.readNext()) != null) {
            if (values.length == 0 || (values.length == 1 && values[0].trim().isEmpty())) {
                continue;
            }

            Map<String, String> record = new HashMap<>();
            for (int i = 0; i < headers.length && i < values.length; i++) {
                record.put(headers[i], values[i] != null ? values[i].trim() : "");
            }
            records.add(record);
        }

        return records;
    }

    private BulkUploadEntry processRecords(Long branchId, String entityType, List<Map<String, String>> records, BulkUploadEntry entry) {
        entry.setTotalRecords(records.size());

        for (int i = 0; i < records.size(); i++) {
            Map<String, String> record = records.get(i);
            int rowNumber = i + 2;

            try {
                if (MemberType.STUDENT.name().equalsIgnoreCase(entityType)) {
                    processStudentRecord(record, branchId);
                } else if (MemberType.INSTRUCTOR.name().equalsIgnoreCase(entityType)) {
                    processInstructorRecord(record, branchId);
                }
                entry.incrementSuccessful();
            } catch (Exception e) {
                entry.incrementFailed();
                entry.getErrorMessages().add(String.format("Row %d: %s", rowNumber, e.getMessage()));
                log.error("Error processing row {}: {}", rowNumber, e.getMessage());
            }
        }

        if (Objects.equals(entry.getProcessedRecords(), entry.getTotalRecords())) {
            if (entry.getFailedRecords() > 0) {
                entry.setStatus("FAILED");
            } else {
                entry.setStatus("COMPLETED");
            }
            entry.setCompletedAt(DateUtil.getCurrentDateUTC());
        }

        return entry;
    }

    private void processStudentRecord(Map<String, String> record, Long branchId) throws Exception {
        StudentEntry studentEntry = new StudentEntry();
        studentEntry.setName(record.get("name"));
        studentEntry.setEmail(record.get("email"));
        studentEntry.setPhone(record.get("phone"));

        if (record.containsKey("address")) {
            studentEntry.setAddress(record.get("address"));
        }
        if (record.containsKey("emergencycontactnumber")) {
            String emergencyContact = record.get("emergencycontactnumber");
            studentEntry.setEmergencyContactNumber(emergencyContact);
        }

        studentEntry.setBranchId(branchId);
        studentManager.add(studentEntry);
    }

    private void processInstructorRecord(Map<String, String> record, Long branchId) throws Exception {
        InstructorEntry instructorEntry = new InstructorEntry();

        instructorEntry.setName(record.get("name").trim());
        instructorEntry.setEmail(record.get("email").trim());
        instructorEntry.setPhone(record.get("phone").trim());

        setOptionalField(record, "address", instructorEntry::setAddress);
        setOptionalField(record, "imageurl", instructorEntry::setImageUrl);

        if (record.containsKey("emergencycontactnumber")) {
            String emergencyContact = record.get("emergencycontactnumber");
            instructorEntry.setEmergencyContactNumber(emergencyContact);
        }

        instructorEntry.setBranchId(branchId);

        instructorManager.add(instructorEntry);
    }

    private void setOptionalField(Map<String, String> record, String fieldName, java.util.function.Consumer<String> setter) {
        if (record.containsKey(fieldName)) {
            String value = record.get(fieldName);
            if (value != null && !value.trim().isEmpty()) {
                setter.accept(value.trim());
            }
        }
    }


    @SneakyThrows
    private BulkUploadEntry convertToEntry(BulkUpload entity) throws EntityNotFoundException {
        if (entity == null) {
            return null;
        }

        BulkUploadEntry entry = new BulkUploadEntry();
        entry.setId(entity.getId());

        if (entity.getBranch() != null) {
            BranchEntry branchEntry = branchManager.getById(entity.getBranch().getId());
            entry.setBranchEntry(branchEntry);
        }

        entry.setEntityType(entity.getEntityType());
        entry.setStatus(entity.getStatus());
        entry.setTotalRecords(entity.getTotalRecords());
        entry.setProcessedRecords(entity.getProcessedRecords());
        entry.setSuccessfulRecords(entity.getSuccessfulRecords());
        entry.setFailedRecords(entity.getFailedRecords());
        entry.setFileUrl(entity.getFileUrl());
        entry.setFileName(entity.getFileName());
        entry.setCompletedAt(entity.getCompletedAt());

        if (entity.getErrorMessage() != null && !entity.getErrorMessage().isEmpty()) {
            entry.setErrorMessages(Arrays.asList(entity.getErrorMessage().split("; ")));
        }

        return entry;
    }

    private BulkUpload convertToEntity(BulkUploadEntry entry, BulkUpload existingEntity) throws Exception {
        BulkUpload entity = (existingEntity != null) ? existingEntity : new BulkUpload();

        if (entry.getBranchEntry() != null && entry.getBranchEntry().getBranchId() != null) {
            BranchEntry branchEntry = branchManager.getById(entry.getBranchEntry().getBranchId());

            entity.setBranch(ConvertToEntryUtil.convertToEntity(branchEntry, null));
        }

        if (entry.getEntityType() != null) {
            entity.setEntityType(entry.getEntityType());
        }
        if (entry.getStatus() != null) {
            entity.setStatus(entry.getStatus());
        } else {
            entity.setStatus("PENDING");
        }

        if (entry.getTotalRecords() != null) {
            entity.setTotalRecords(entry.getTotalRecords());
        }
        if (entry.getProcessedRecords() != null) {
            entity.setProcessedRecords(entry.getProcessedRecords());
        }
        if (entry.getSuccessfulRecords() != null) {
            entity.setSuccessfulRecords(entry.getSuccessfulRecords());
        }
        if (entry.getFailedRecords() != null) {
            entity.setFailedRecords(entry.getFailedRecords());
        }
        if (entry.getFileUrl() != null) {
            entity.setFileUrl(entry.getFileUrl());
        }
        if (entry.getFileName() != null) {
            entity.setFileName(entry.getFileName());
        }
        if (entry.getCompletedAt() != null) {
            entity.setCompletedAt(entry.getCompletedAt());
        }
        if (entry.getErrorMessages() != null && !entry.getErrorMessages().isEmpty()) {
            entity.setErrorMessage(String.join("; ", entry.getErrorMessages()));
        }

        return entity;
    }

}
