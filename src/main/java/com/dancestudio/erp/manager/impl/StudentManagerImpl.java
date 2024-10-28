package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.manager.EmailManager;
import com.dancestudio.erp.manager.StudentManager;
import com.dancestudio.erp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentManagerImpl implements StudentManager {

    private final StudentRepository studentRepository;

    @Autowired
    private EmailManager emailManager;

    @Autowired
    public StudentManagerImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEntry addStudent(StudentEntry studentEntry) {
        Student student = convertToEntity(studentEntry);
        sendEmail(student);
        return convertToEntry(studentRepository.save(student));
    }

    @Override
    public StudentEntry updateStudent(Long studentId, StudentEntry studentEntry) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        sendEmail(student);
        Student newStudentEntry = convertToEntity(studentEntry);
        return convertToEntry(studentRepository.save(newStudentEntry));
    }

    @Override
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public StudentEntry getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return convertToEntry(student);
    }

    @Override
    public List<StudentEntry> getAllStudents() {
        List<Student> entries = studentRepository.findAll().stream().collect(Collectors.toList());

        List<StudentEntry> studentEntries = new ArrayList<>();
        for (Student entry : entries) {
            StudentEntry studentEntry = convertToEntry(entry);
            studentEntries.add(studentEntry);
        }

        return studentEntries;
    }

    @Async
    public void sendEmail(Student student) {
        try {
            String subject = "Welcome to Dance Studio";
            String body = "Dear " + student.getName() + ",\n\nWelcome! You have been successfully registered.";
            emailManager.sendEmail(student.getEmail(), subject, body);
            student.setEmailSent(true);
            studentRepository.save(student);  // Update email sent status
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    public void resendEmail(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        sendEmail(student);
    }


    private StudentEntry convertToEntry(Student student) {

        StudentEntry studentEntry = new StudentEntry();
        studentEntry.setStudentId(student.getId());
        studentEntry.setName(student.getName());
        studentEntry.setPhone(student.getPhone());
        studentEntry.setProfileDetails(student.getProfileDetails());
        studentEntry.setRegistrationDate(student.getRegistrationDate());
        studentEntry.setMembershipStatus(student.getMembershipStatus());
        studentEntry.setStudioId(student.getStudio().getId());

        return studentEntry;
    }

    private Student convertToEntity(StudentEntry studentEntry) {

        Student student = new Student();
        student.setId(studentEntry.getStudentId());
        student.setName(studentEntry.getName());
        student.setEmail(studentEntry.getEmail());
        student.setPhone(studentEntry.getPhone());
        student.setProfileDetails(studentEntry.getProfileDetails());
        student.setRegistrationDate(studentEntry.getRegistrationDate());
        student.setMembershipStatus(studentEntry.getMembershipStatus());

        return student;
    }
}
