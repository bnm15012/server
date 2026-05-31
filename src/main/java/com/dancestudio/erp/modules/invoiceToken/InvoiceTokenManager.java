package com.dancestudio.erp.modules.invoiceToken;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.booking.Booking;
import com.dancestudio.erp.modules.booking.BookingConvertor;
import com.dancestudio.erp.modules.booking.BookingEntry;
import com.dancestudio.erp.modules.branch.BranchConvertor;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.student.StudentConvertor;
import com.dancestudio.erp.modules.member.student.StudentEntry;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignment;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentConvertor;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentEntry;
import com.dancestudio.erp.modules.studio.StudioConvertor;
import com.dancestudio.erp.modules.template.genericTemplate.GenricTemplateEntry;
import com.dancestudio.erp.modules.template.genericTemplate.GenricTemplateManager;

@Component
public class InvoiceTokenManager {

    @Autowired
    private InvoiceTokenRepository invoiceTokenRepository;

    @Autowired
    private GenricTemplateManager genricTemplateManager;

    public InvoiceTokenResponse getInvoiceDataByToken(String token) throws Exception {
        InvoiceToken invoiceToken = invoiceTokenRepository.findByInvoiceToken(UUID.fromString(token))
                .orElseThrow(() -> new EntityNotFoundException("Invoice token not found"));

        if (invoiceToken.getExpiresAt().before(new Date())) {
            invoiceTokenRepository.delete(invoiceToken);
            throw new EntityNotFoundException("Invoice token has expired");
        }

        InvoiceTokenResponse response = new InvoiceTokenResponse();
        StudentActivityAssignment assignment = invoiceToken.getStudentActivityAssignment();
        Branch branch = null;
        if (Objects.nonNull(assignment)) {
            Member student = assignment.getStudent();
            if (student == null) {
                throw new EntityNotFoundException("Associated student not found");
            }

            StudentEntry studentEntry = StudentConvertor.convertToEntry(student);
            branch = student.getBranch();
            response.setStudent(studentEntry);
        } else {
            Booking booking = invoiceToken.getBooking();
            if (booking == null) {
                throw new EntityNotFoundException("Associated booking not found");
            }
            BookingEntry bookingEntry = BookingConvertor.convertToEntry(booking);
            List<GenricTemplateEntry> content = genricTemplateManager
                    .getAllConditionsByStudioId(booking.getBranch().getStudio().getId(), "BOOKING", 0, -1).getContent();
            branch = booking.getBranch();
            response.setBooking(bookingEntry);
            response.setTemplate(content.get(0));
        }

        StudentActivityAssignmentEntry assignmentEntry = StudentActivityAssignmentConvertor.convertToEntry(assignment,
                new String[] {});

        BranchEntry branchEntry = BranchConvertor.convertToEntry(branch,
                new String[] { "address", "city", "state", "pincode", "phone" });

        Studio studio = branch.getStudio();

        StudioEntry studioEntry = StudioConvertor.convertToEntry(studio, new String[] {
                "studioName",
                "email",
                "gstNumber",
                "logo",
        });

        response.setAssignment(assignmentEntry);
        response.setBranch(branchEntry);
        response.setStudio(studioEntry);
        response.setStatus(new com.dancestudio.erp.response.StatusResponse(1, "Invoice data retrieved successfully",
                com.dancestudio.erp.response.StatusResponse.Type.SUCCESS));
        return response;
    }

    public InvoiceToken addInvoiceToken(StudentActivityAssignment assignment) {
        InvoiceToken token = new InvoiceToken();
        token.setStudentActivityAssignment(assignment);
        token.setCreatedAt(new java.util.Date());

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(token.getCreatedAt());
        cal.add(java.util.Calendar.MONTH, 1);
        token.setExpiresAt(cal.getTime());
        return invoiceTokenRepository.save(token);
    }

    public InvoiceToken addInvoiceToken(Booking booking) {
        InvoiceToken token = new InvoiceToken();
        token.setBooking(booking);
        token.setCreatedAt(new java.util.Date());

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(token.getCreatedAt());
        cal.add(java.util.Calendar.MONTH, 1);
        token.setExpiresAt(cal.getTime());
        return invoiceTokenRepository.save(token);
    }
}
