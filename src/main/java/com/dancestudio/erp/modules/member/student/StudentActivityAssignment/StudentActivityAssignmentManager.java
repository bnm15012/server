package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.enums.ActivityType;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.expense.ExpenseCategory;
import com.dancestudio.erp.modules.expense.ExpenseEntry;
import com.dancestudio.erp.modules.expense.ExpenseRepository;
import com.dancestudio.erp.modules.invoiceToken.InvoiceToken;
import com.dancestudio.erp.modules.invoiceToken.InvoiceTokenManager;
import com.dancestudio.erp.modules.invoiceToken.InvoiceTokenResponse;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.member.attendance.AttendanceReqDTO;
import com.dancestudio.erp.modules.member.attendance.AttendanceUtils;
import com.dancestudio.erp.modules.member.memberActiveStatus.MemberActiveStatusUtil;
import com.dancestudio.erp.modules.payments.PaymentManager;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;
import lombok.SneakyThrows;

@Service
@Setter
@Transactional(rollbackFor = Exception.class)
public class StudentActivityAssignmentManager
        extends BaseManager<StudentActivityAssignment, Long, StudentActivityAssignmentEntry> {

    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;

    @Autowired
    private PaymentManager paymentManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private InvoiceTokenManager invoiceTokenManager;

    protected StudentActivityAssignmentManager(StudentActivityAssignmentRepository repository) {
        super(repository, "StudentActivityAssignment");
        this.studentActivityAssignmentRepository = repository;
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

        // Generate and save token
        InvoiceToken token = invoiceTokenManager.addInvoiceToken(studentStudentActivityAssignmentAssignment);
        studentStudentActivityAssignmentAssignment.setStudentInvoiceToken(token);

        MemberActiveStatusUtil.addNewAssignment(studentStudentActivityAssignmentAssignment.getStudent(),
                studentActivityAssignmentEntry.getMembershipStartDate(),
                studentActivityAssignmentEntry.getMembershipEndDate());

        try {
            Long payeeId = studentStudentActivityAssignmentAssignment.getId();
            PaymentEntry paymentEntry = studentActivityAssignmentEntry.getPaymentEntry();
            if (paymentEntry == null) {
                throw new EntityNotFoundException("Payment details not provided");
            }
            paymentEntry.setPayeeId(payeeId);
            paymentEntry.setPayeeType(PayeeType.STUDENT);
            paymentEntry = paymentManager.add(paymentEntry);
            studentActivityAssignmentEntry = StudentActivityAssignmentConvertor
                    .convertToEntry(studentStudentActivityAssignmentAssignment, new String[] {});
            studentActivityAssignmentEntry.setPaymentEntry(paymentEntry);
            return studentActivityAssignmentEntry;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException(
                    ex.getMessage() != null ? "Failed to add payment details: " + ex.getMessage()
                            : "Failed to add payment details");
        }
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        StudentActivityAssignment studentActivityAssignment = studentActivityAssignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));
        super.delete(id);
        try {
            MemberActiveStatusUtil.deleteNewAssignment(studentActivityAssignment.getStudent(),
                    studentActivityAssignment.getMembershipStartDate(),
                    studentActivityAssignment.getMembershipEndDate());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StudentActivityAssignmentEntry update(Long id, StudentActivityAssignmentEntry entry)
            throws EntityNotFoundException, BeansException, Exception {

        StudentActivityAssignment studentActivityAssignment = studentActivityAssignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudentActivityAssignment not found"));
        entry = super.update(id, entry);
        MemberActiveStatusUtil.updateNewAssignment(
                studentActivityAssignment.getStudent(),
                studentActivityAssignment.getMembershipStartDate(),
                studentActivityAssignment.getMembershipEndDate(),
                entry.getMembershipStartDate(),
                entry.getMembershipEndDate());
        return entry;
    }

    public StudentActivityAssignmentEntry getStudentAssignmentsByStudentAndActivityId(Long studentId,
            String activityName) throws Exception {
        StudentActivityAssignment assignment = studentActivityAssignmentRepository
                .findByStudentIdAndActivityId(studentId, activityName);

        return StudentActivityAssignmentConvertor.convertToEntry(assignment, new String[] {});
    }

    public List<StudentActivityAssignmentEntry> getStudentAssignmentsByStudentId(Long studentId) throws Exception {
        List<StudentActivityAssignment> enrollments = studentActivityAssignmentRepository.findByStudentId(studentId);
        List<StudentActivityAssignmentEntry> entries = new ArrayList<>();

        for (StudentActivityAssignment enrollment : enrollments) {
            entries.add(StudentActivityAssignmentConvertor.convertToEntry(enrollment, new String[] {}));
        }

        return entries;
    }

    public List<StudentActivityAssignmentEntry> getStudentByActivityIdAndStudioIdAndStatus(String activityName,
            Long studioId, String status) throws Exception {

        List<StudentActivityAssignment> entries = studentActivityAssignmentRepository
                .findStudentsWithActiveMemberships(activityName, studioId);
        List<StudentActivityAssignmentEntry> assignmentEntries = new ArrayList<>();

        for (StudentActivityAssignment entry : entries) {
            boolean isActive = entry.getMembershipEndDate().after(DateUtil.getCurrentDateUTC());
            if ((status.equalsIgnoreCase("ACTIVE") && isActive) || (status.equalsIgnoreCase("INACTIVE") && !isActive)) {
                assignmentEntries.add(StudentActivityAssignmentConvertor.convertToEntry(entry, new String[] {}));
            }
        }
        return assignmentEntries;
    }

    @SneakyThrows
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
        return paymentManager.getAll(studioId, 0, -1,
                monthRange.get("start"), monthRange.get("end"), null, null, null).getContent();

    }

    private double calculateRevenueForMonth(List<MonthlyReportEntry> reportEntries, int month) {
        return reportEntries.stream().filter(entry -> entry.getMonth() == month)
                .mapToDouble(MonthlyReportEntry::getRevenue)
                .sum();
    }

    @Override
    protected StudentActivityAssignment toEntity(StudentActivityAssignmentEntry entry,
            StudentActivityAssignment existing) throws EntityNotFoundException, BeansException, Exception {
        return StudentActivityAssignmentConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected StudentActivityAssignmentEntry toEntry(StudentActivityAssignment entity, String[] fields)
            throws EntityNotFoundException {
        return StudentActivityAssignmentConvertor.convertToEntry(entity, fields);
    }

    public StudentActivityAssignmentEntry markAttendance(Long activityAssignmentId) throws Exception {
        StudentActivityAssignment assignment = studentActivityAssignmentRepository
                .findById(activityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Activity assignment not found"));
        if (!MemberActiveStatusUtil.isMembershipActive(assignment.getMembershipStartDate(),
                assignment.getMembershipEndDate())) {
            throw new RuntimeException("Membership is not active");
        }
        toggleAttendance(assignment, DateUtil.getCurrentDateUTC(), true);
        return toEntry(studentActivityAssignmentRepository.save(assignment),
                new String[] { "attendanceEntries" });
    }

    public List<StudentActivityAssignmentEntry> markAttendanceBulk(
            AttendanceReqDTO reqDTO) throws Exception {

        List<StudentActivityAssignment> assignments = studentActivityAssignmentRepository
                .findAllById(reqDTO.getActivityAssignmentIds());

        if (assignments.size() != reqDTO.getActivityAssignmentIds().size()) {
            throw new EntityNotFoundException("Some assignments not found");
        }

        Date today = DateUtil.getUTCDate(reqDTO.getDate());

        for (StudentActivityAssignment assignment : assignments) {

            if (!MemberActiveStatusUtil.isMembershipActive(
                    assignment.getMembershipStartDate(),
                    assignment.getMembershipEndDate())) {

                throw new RuntimeException(
                        "Membership inactive for assignment id: "
                                + assignment.getId());
            }
            toggleAttendance(assignment, today, reqDTO.isPresent());
        }

        List<StudentActivityAssignment> saved = studentActivityAssignmentRepository.saveAll(assignments);
        return saved.stream()
                .map(arg0 -> {
                    try {
                        return toEntry(arg0, new String[] { "attendanceEntries" });
                    } catch (EntityNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .toList();
    }

    public void toggleAttendance(StudentActivityAssignment assignment, Date date, boolean preset) {
        assignment.setAttendanceBitmap(AttendanceUtils.toggleAttendance(
                assignment.getAttendanceBitmap(), assignment.getMembershipStartDate(),
                assignment.getMembershipEndDate(),
                date, preset));
    }

    public Page<StudentActivityAssignment> getAssignmentsByCriteria(
            Long rootId,
            String rootType,
            ActivityType activityName,
            String searchText,
            LocalDate date,
            Integer page,
            Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<StudentActivityAssignment> spec = StudentActivityAssignmentSpecification
                .getAssignmentsByCriteria(
                        rootId, rootType, activityName, searchText, date);
        return studentActivityAssignmentRepository.findAll(spec, pageable);
    }

    public InvoiceTokenResponse getInvoiceDataByToken(String token) throws Exception {
        InvoiceTokenResponse response = invoiceTokenManager.getInvoiceDataByToken(token);
        return response;
    }
}
