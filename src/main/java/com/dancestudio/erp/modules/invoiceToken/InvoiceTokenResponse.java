package com.dancestudio.erp.modules.invoiceToken;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.modules.booking.BookingEntry;
import com.dancestudio.erp.modules.member.student.StudentEntry;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentEntry;
import com.dancestudio.erp.modules.template.genericTemplate.GenricTemplateEntry;
import com.dancestudio.erp.response.AbstractResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceTokenResponse extends AbstractResponse {
    private StudentEntry student;
    private StudentActivityAssignmentEntry assignment;
    private StudioEntry studio;
    private BranchEntry branch;
    private BookingEntry booking;
    private GenricTemplateEntry template;
}
