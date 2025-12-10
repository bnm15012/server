package com.dancestudio.erp.modules.member.student;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.memberActiveStatus.MemberActiveStatusManager;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import jakarta.annotation.PostConstruct;

@Component
public class StudentConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static StudentEntry convertToEntry(Member student) {

        StudentEntry studentEntry = new StudentEntry();
        studentEntry.setStudentId(student.getId());
        studentEntry.setName(student.getName());
        studentEntry.setPhone(student.getPhone());
        studentEntry.setDob(student.getDob());
        studentEntry.setEmail(student.getEmail());
        studentEntry.setImageUrl(student.getProfileImage());
        studentEntry.setAddress(student.getAddress());
        studentEntry.setEmergencyContactNumber(student.getEmergencyContactNumber());
        try {
            MemberActiveStatusManager activityStatusManager = applicationContext
                    .getBean(MemberActiveStatusManager.class);
            boolean isActive = activityStatusManager.isMemberActive(student.getId());
            studentEntry.setMembershipStatus(isActive ? MembershipStatus.ACTIVE : MembershipStatus.INACTIVE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentEntry;
    }

    public static Member convertToEntity(StudentEntry studentEntry, Member existingStudent) throws Exception {
        Member student = (existingStudent != null) ? existingStudent : new Member();

        // add identifier as STUDENT
        student.setMemberType(MemberType.STUDENT.name());

        if (Objects.nonNull(studentEntry.getStudentId())) {
            student.setId(studentEntry.getStudentId());
        }
        if (Objects.nonNull(studentEntry.getName())) {
            student.setName(studentEntry.getName());
        }
        if (Objects.nonNull(studentEntry.getEmail())) {
            student.setEmail(studentEntry.getEmail());
        }
        if (Objects.nonNull(studentEntry.getPhone())) {
            student.setPhone(studentEntry.getPhone());
        }
        if (Objects.nonNull(studentEntry.getDob())) {
            student.setDob(studentEntry.getDob());
        }
        if (Objects.nonNull(studentEntry.getImageUrl())) {
            student.setProfileImage(studentEntry.getImageUrl());
        }
        if (Objects.nonNull(studentEntry.getAddress())) {
            student.setAddress(studentEntry.getAddress());
        }
        if (Objects.nonNull(studentEntry.getEmergencyContactNumber())) {
            student.setEmergencyContactNumber(studentEntry.getEmergencyContactNumber());
        }
        if (Objects.nonNull(studentEntry.getBranchId())) {
            BranchManager branchManager = applicationContext
                    .getBean(BranchManager.class);
            BranchEntry entry = branchManager.getById(studentEntry.getBranchId());
            student.setBranch(ConvertToEntryUtil.convertToEntity(entry, null));
        }

        return student;
    }

}
