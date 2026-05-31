package com.dancestudio.erp.modules.invoiceToken;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.branch.BranchConvertor;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.student.StudentConvertor;
import com.dancestudio.erp.modules.member.student.StudentEntry;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignment;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentConvertor;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentEntry;
import com.dancestudio.erp.modules.studio.StudioConvertor;

@Component
public class InvoiceTokenManager {

    @Autowired
    private InvoiceTokenRepository invoiceTokenRepository;

    public InvoiceTokenResponse getInvoiceDataByToken(String token) throws Exception {
        InvoiceToken invoiceToken = invoiceTokenRepository.findByInvoiceToken(UUID.fromString(token))
                .orElseThrow(() -> new EntityNotFoundException("Invoice token not found"));

        if (invoiceToken.getExpiresAt().before(new Date())) {
            invoiceTokenRepository.delete(invoiceToken);
            throw new EntityNotFoundException("Invoice token has expired");
        }

        StudentActivityAssignment assignment = invoiceToken.getStudentActivityAssignment();
        if (assignment == null) {
            throw new EntityNotFoundException("Associated assignment not found");
        }

        Member student = assignment.getStudent();
        if (student == null) {
            throw new EntityNotFoundException("Associated student not found");
        }

        StudentEntry studentEntry = StudentConvertor.convertToEntry(student);

        StudentActivityAssignmentEntry assignmentEntry = StudentActivityAssignmentConvertor.convertToEntry(assignment,
                new String[] {});

        Branch branch = student.getBranch();

        BranchEntry branchEntry = BranchConvertor.convertToEntry(branch,
                new String[] { "address", "city", "state", "pincode", "phone" });

        Studio studio = branch.getStudio();

        StudioEntry studioEntry = StudioConvertor.convertToEntry(studio, new String[] {
                "studioName",
                "email",
                "gstNumber",
                "logo",
        });

        InvoiceTokenResponse response = new InvoiceTokenResponse();
        response.setStudent(studentEntry);
        response.setAssignment(assignmentEntry);
        response.setBranch(branchEntry);
        response.setStudio(studioEntry);
        response.setStatus(new com.dancestudio.erp.response.StatusResponse(1, "Invoice data retrieved successfully",
                com.dancestudio.erp.response.StatusResponse.Type.SUCCESS));
        return response;
    }
}
