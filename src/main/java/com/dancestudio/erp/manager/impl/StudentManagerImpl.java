package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EmailManager;
import com.dancestudio.erp.manager.StudentManager;
import com.dancestudio.erp.repository.StudentRepository;
import com.dancestudio.erp.service.impl.AzureBlobUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StudentManagerImpl implements StudentManager {

    private final StudentRepository studentRepository;

    @Autowired
    private EmailManager emailManager;

    @Autowired
    private AzureBlobUploadService azureBlobUploadService;

    @Autowired
    public StudentManagerImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEntry addStudent(StudentEntry studentEntry) throws Exception {
        Student student = convertToEntity(studentEntry);

        String imageUrl = azureBlobUploadService.uploadImageToBlob(studentEntry.getStudioId(), "Students", studentEntry.getName(), studentEntry.getProfileImage());
        student.setProfileImage(imageUrl);
        studentRepository.save(student);

        emailManager.sendRegistrationEmail(student);
        return convertToEntry(student);
    }

    @Override
    public StudentEntry updateStudent(Long studentId, StudentEntry studentEntry) throws EntityNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (Objects.nonNull(studentEntry.getProfileImage())) {
            String imageUrl = azureBlobUploadService.uploadImageToBlob(studentEntry.getStudioId(), "Students", studentEntry.getName(), studentEntry.getProfileImage());
            student.setProfileImage(imageUrl);
        }
        Student newStudentEntry = convertToEntity(studentEntry);
        return convertToEntry(studentRepository.save(newStudentEntry));
    }

    @Override
    public void deleteStudent(Long studentId) throws EntityNotFoundException {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        studentRepository.deleteById(studentId);
    }

    @Override
    public StudentEntry getStudentById(Long studentId) throws EntityNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return convertToEntry(student);
    }

    @Override
    public List<StudentEntry> getAllStudentsByStudio(Long studioId, Long activityId, MembershipStatus membershipStatus) {
        List<Student> entries = studentRepository.findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(studioId, activityId, membershipStatus);

        List<StudentEntry> studentEntries = new ArrayList<>();
        for (Student entry : entries) {
            StudentEntry studentEntry = convertToEntry(entry);
            studentEntries.add(studentEntry);
        }

        return studentEntries;
    }

    @Override
    public List<StudentEntry> findByMembershipEndDate(LocalDate reminderDate) {
        List<Student> entries = studentRepository.findByMembershipEndDate(reminderDate);

        List<StudentEntry> studentEntries = new ArrayList<>();
        for (Student entry : entries) {
            StudentEntry studentEntry = convertToEntry(entry);
            studentEntries.add(studentEntry);
        }

        return studentEntries;
    }

    @Override
    public boolean sendSubscriptionRenewalReminder(Long studentId) throws EntityNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        emailManager.sendSubscriptionRenewalEmail(student);
        return true;
    }


    public Boolean checkIfStudentExistsinStudio(Long studioId) {
        return studentRepository.studentsExistsByStudioId(studioId);
    }

    private StudentEntry convertToEntry(Student student) {

        StudentEntry studentEntry = new StudentEntry();
        studentEntry.setStudentId(student.getId());
        studentEntry.setName(student.getName());
        studentEntry.setPhone(student.getPhone());

//        byte[] imageBytes = azureBlobUploadService.getImageInBytes(student.getProfileImage());

        byte[] imageBytes = null;
        studentEntry.setProfileImage(imageBytes);

        studentEntry.setRegistrationDate(student.getRegistrationDate());
        studentEntry.setMembershipStatus(student.getStatus());
        studentEntry.setStudioId(student.getStudio().getId());

        List<String> activityNames = student.getEnrolledActivityNames();
        studentEntry.setEnrolledActivities(activityNames);

        return studentEntry;
    }

    private Student convertToEntity(StudentEntry studentEntry) {

        Student student = new Student();
        student.setId(studentEntry.getStudentId());
        student.setName(studentEntry.getName());
        student.setEmail(studentEntry.getEmail());
        student.setPhone(studentEntry.getPhone());
        student.setProfileImage(studentEntry.getImageUrl());
        student.setRegistrationDate(studentEntry.getRegistrationDate());
        student.setStatus(studentEntry.getMembershipStatus());

        return student;
    }
}
