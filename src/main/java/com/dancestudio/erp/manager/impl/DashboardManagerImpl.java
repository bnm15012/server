package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.DashboardEntry;
import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.DashboardManager;
import com.dancestudio.erp.manager.InstructorManager;
import com.dancestudio.erp.manager.StudentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardManagerImpl implements DashboardManager {

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private InstructorManager instructorManager;

    @Override
    public DashboardEntry getDashboardDetails(Long studioId) throws EntityNotFoundException {
        DashboardEntry entry = new DashboardEntry();

        List<StudentEntry> studentEntryList = studentManager.getAllStudentsByStudio(studioId, null, null, 0, -1);
        entry.setTotalStudents((long) studentEntryList.size());

        List<InstructorEntry> instructorEntryList = instructorManager.getAllInstructorsByStudio(studioId, null);
        entry.setTotalInstructors((long) instructorEntryList.size());

        studentEntryList = studentManager.getStudentByActivityIdAndStudioIdAndStatus(null, studioId, MembershipStatus.ACTIVE.name());
        entry.setTotalActiveMemberships((long) studentEntryList.size());

        return entry;
    }

}