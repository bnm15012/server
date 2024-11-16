package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardManagerImpl implements DashboardManager {

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private InstructorManager instructorManager;

    @Autowired
    private ExpenseManager expenseManager;

    @Autowired
    private StudentActivityAssignmentManager studentActivityAssignmentManager;


    @Override
    public DashboardEntry getDashboardDetails(Long studioId, Long startMonth, Long endMonth) throws EntityNotFoundException {
        DashboardEntry entry = new DashboardEntry();

        List<StudentEntry> studentEntryList = studentManager.getAllStudentsByStudio(studioId, null, null, 0, -1);
        entry.setTotalStudents((long) studentEntryList.size());

        List<InstructorEntry> instructorEntryList = instructorManager.getAllInstructorsByStudio(studioId, null, 0, -1);
        entry.setTotalInstructors((long) instructorEntryList.size());

        List<StudentActivityAssignmentEntry> activeMembershipEntryList = studentActivityAssignmentManager.getStudentByActivityIdAndStudioIdAndStatus(null, studioId, MembershipStatus.ACTIVE.name());
        entry.setTotalActiveMemberships((long) activeMembershipEntryList.size());

        List<ExpenseEntry> expenseEntryList = expenseManager.getAllExpenses(studioId, startMonth, endMonth);
        entry.setTotalExpenseCount((long) expenseEntryList.size());

        return entry;
    }

}