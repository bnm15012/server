package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.expense.ExpenseCategory;
import com.dancestudio.erp.modules.expense.ExpenseEntry;
import com.dancestudio.erp.modules.expense.ExpenseRepository;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.payments.PaymentManager;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;
import com.dancestudio.erp.util.DateUtil;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Setter
public class StudentActivityAssignmentManagerImpl implements StudentActivityAssignmentManager {
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;

    @Autowired
    private PaymentManager paymentManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public StudentActivityAssignmentManagerImpl(
            StudentActivityAssignmentRepository studentActivityAssignmentRepository) {
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
    }

    @Override
    public StudentActivityAssignmentEntry add(StudentActivityAssignmentEntry studentActivityAssignmentEntry)
            throws Exception {
        memberRepository.findById(studentActivityAssignmentEntry.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        StudentActivityAssignment studentStudentActivityAssignmentAssignment = StudentActivityAssignmentConvertor
                .convertToEntity(
                        studentActivityAssignmentEntry, null);
        studentStudentActivityAssignmentAssignment = studentActivityAssignmentRepository
                .save(studentStudentActivityAssignmentAssignment);

        try {
            Long payeeId = studentStudentActivityAssignmentAssignment.getId();
            PaymentEntry paymentEntry = studentActivityAssignmentEntry.getPaymentEntry();
            paymentEntry.setPayeeId(payeeId);
            paymentEntry.setPayeeType(PayeeType.STUDENT);
            paymentEntry = paymentManager.add(paymentEntry);
            studentActivityAssignmentEntry = StudentActivityAssignmentConvertor
                    .convertToEntry(studentStudentActivityAssignmentAssignment);
            studentActivityAssignmentEntry.setPaymentEntry(paymentEntry);
            return studentActivityAssignmentEntry;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException("Failed to add payment details");
        }
    }

    @Override
    public StudentActivityAssignmentEntry update(Long studentActivityAssignmentId,
            StudentActivityAssignmentEntry studentActivityAssignmentEntry) throws Exception {
        StudentActivityAssignment existingStudentActivityAssignment = studentActivityAssignmentRepository
                .findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        StudentActivityAssignment updatedStudentActivityAssignment = StudentActivityAssignmentConvertor.convertToEntity(
                studentActivityAssignmentEntry,
                existingStudentActivityAssignment);
        updatedStudentActivityAssignment = studentActivityAssignmentRepository.save(updatedStudentActivityAssignment);

        return StudentActivityAssignmentConvertor
                .convertToEntry(studentActivityAssignmentRepository.save(updatedStudentActivityAssignment));
    }

    @Override
    public void delete(Long studentActivityAssignmentId) throws EntityNotFoundException {
        studentActivityAssignmentRepository.findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        studentActivityAssignmentRepository.deleteById(studentActivityAssignmentId);
    }

    @Override
    public StudentActivityAssignmentEntry getById(Long studentActivityAssignmentId) throws Exception {
        StudentActivityAssignment studentActivityAssignment = studentActivityAssignmentRepository
                .findById(studentActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));

        return StudentActivityAssignmentConvertor.convertToEntry(studentActivityAssignment);
    }

    @Override
    public StudentActivityAssignmentEntry getStudentAssignmentsByStudentAndActivityId(Long studentId,
            String activityName) throws Exception {
        StudentActivityAssignment assignment = studentActivityAssignmentRepository
                .findByStudentIdAndActivityId(studentId, activityName);
        return StudentActivityAssignmentConvertor.convertToEntry(assignment);
    }

    @Override
    public List<StudentActivityAssignmentEntry> getStudentAssignmentsByStudentId(Long studentId) throws Exception {
        List<StudentActivityAssignment> enrollments = studentActivityAssignmentRepository.findByStudentId(studentId);
        List<StudentActivityAssignmentEntry> entries = new ArrayList<>();

        for (StudentActivityAssignment enrollment : enrollments) {
            entries.add(StudentActivityAssignmentConvertor.convertToEntry(enrollment));
        }

        return entries;
    }

    @Override
    public List<StudentActivityAssignmentEntry> getStudentByActivityIdAndStudioIdAndStatus(String activityName,
            Long studioId, String status) throws Exception {

        List<StudentActivityAssignment> entries = studentActivityAssignmentRepository
                .findStudentsWithActiveMemberships(activityName, studioId);
        List<StudentActivityAssignmentEntry> assignmentEntries = new ArrayList<>();

        for (StudentActivityAssignment entry : entries) {
            boolean isActive = entry.getMembershipEndDate().after(DateUtil.getCurrentDateUTC());
            if ((status.equalsIgnoreCase("ACTIVE") && isActive) || (status.equalsIgnoreCase("INACTIVE") && !isActive)) {
                assignmentEntries.add(StudentActivityAssignmentConvertor.convertToEntry(entry));
            }
        }
        return assignmentEntries;
    }

    @SneakyThrows
    @Override
    public List<MonthlyReportEntry> getAnalysisReport(Integer year, Long branchId) {
        List<MonthlyReportEntry> reportEntries = studentActivityAssignmentRepository
                .getAnalysisReport(Math.toIntExact(year), branchId);
        for (int month = 1; month <= 12; month++) {
            processMonthlyReport(reportEntries, month, year, branchId);
        }

        reportEntries.sort(Comparator.comparingInt(MonthlyReportEntry::getMonth));
        return reportEntries;
    }

    private void processMonthlyReport(List<MonthlyReportEntry> reportEntries, Integer month, Integer year,
            Long branchId) throws EntityNotFoundException {
        List<ExpenseEntry> expenseEntries = getExpenseEntriesForMonth(month, year, branchId);
        List<PaymentEntry> paymentEntries = getPaymentEntriesForMonth(month, year, branchId);
        double revenue = calculateRevenueForMonth(reportEntries, month);

        MonthlyReportEntry monthlyReportEntry = findOrCreateMonthlyReportEntry(reportEntries, month, revenue);
        monthlyReportEntry.setExpenseEntries(expenseEntries);
        monthlyReportEntry.setPaymentEntries(paymentEntries);
        monthlyReportEntry.setRevenue(revenue);
    }

    private MonthlyReportEntry findOrCreateMonthlyReportEntry(List<MonthlyReportEntry> reportEntries, int month,
            double revenue) {
        return reportEntries.stream().filter(entry -> entry.getMonth() == month)
                .findFirst()
                .orElseGet(() -> {
                    MonthlyReportEntry newEntry = new MonthlyReportEntry(month, revenue);
                    reportEntries.add(newEntry);
                    return newEntry;
                });
    }

    private List<ExpenseEntry> getExpenseEntriesForMonth(Integer month, Integer year, Long branchId)
            throws EntityNotFoundException {
        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(month, year, month, year);
        List<Object[]> expenses = expenseRepository.findCategoryWiseSumOfExpensesByDateRangeAndBranchId(
                monthRange.get("start"), monthRange.get("end"), branchId);
        List<ExpenseEntry> expenseEntries = new ArrayList<>();
        for (Object[] expense : expenses) {
            ExpenseEntry expenseEntry = new ExpenseEntry();
            expenseEntry.setExpenseCategory(ExpenseCategory.valueOf((String) expense[0]));
            expenseEntry.setAmount((Double) expense[1]);
            expenseEntries.add(expenseEntry);
        }

        return expenseEntries;
    }

    private List<PaymentEntry> getPaymentEntriesForMonth(Integer month, Integer year, Long studioId) {
        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(month, year, month, year);
        // List<Object[]> payments =
        // paymentRepository.findCategoryWiseSumOfPaymentsByDateRange(studioId,
        // monthRange.get("start"), monthRange.get("end"));
        List<PaymentEntry> paymentEntries = new ArrayList<>();

        // for (Object[] payment : payments) {
        // PaymentEntry paymentEntry = new PaymentEntry();
        // paymentEntry.setPayeeType(PayeeType.valueOf((String) payment[0]));
        // paymentEntry.setAmount((Double) payment[1]);
        // paymentEntries.add(paymentEntry);
        // }

        return paymentEntries;
    }

    private double calculateRevenueForMonth(List<MonthlyReportEntry> reportEntries, int month) {
        return reportEntries.stream().filter(entry -> entry.getMonth() == month)
                .mapToDouble(MonthlyReportEntry::getRevenue)
                .sum();
    }

    @Override
    public Page<StudentActivityAssignment> getAssignmentsByStudentId(Long id, Integer page, Integer size)
            throws Exception {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return studentActivityAssignmentRepository.findActivitiesByStudentId(id, pageable);
    }
}
