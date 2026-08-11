package com.dancestudio.erp.modules.payments;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.modules.booking.Booking;
import com.dancestudio.erp.modules.booking.BookingRepository;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignment;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentRepository;
import com.dancestudio.erp.modules.payments.entity.Payment;
import com.dancestudio.erp.modules.payments.entity.PaymentBooking;
import com.dancestudio.erp.modules.payments.entity.PaymentStudentActivity;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;
import com.dancestudio.erp.repository.BranchRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PaymentConvertor {
    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static PaymentEntry convertToEntry(PaymentBooking paymentBooking) {
        PaymentEntry paymentEntry = new PaymentEntry();
        convertToEntry(paymentBooking.getPayment(), paymentEntry);
        paymentEntry.setPayeeId(paymentBooking.getBooking().getId());
        paymentEntry.setPayeeType(PayeeType.BOOKING);
        paymentEntry.setPayeeName(paymentBooking.getBooking().getClient().getPocName());
        return paymentEntry;
    }

    public static PaymentEntry convertToEntry(PaymentStudentActivity paymentMember) {
        PaymentEntry paymentEntry = new PaymentEntry();
        convertToEntry(paymentMember.getPayment(), paymentEntry);
        paymentEntry.setPayeeId(paymentMember.getStudentActivityAssignment().getId());
        paymentEntry.setPayeeType(PayeeType.STUDENT);
        paymentEntry.setPayeeName(
                paymentMember.getStudentActivityAssignment().getStudent().getName());
        paymentEntry.setActualAmount(paymentMember.getActualAmount());
        return paymentEntry;
    }

    public static PaymentEntry convertToEntry(Payment payment) {
        PaymentEntry paymentEntry = new PaymentEntry();
        convertToEntry(payment, paymentEntry);
        if (payment.getPaymentBooking() != null) {
            paymentEntry.setPayeeType(PayeeType.BOOKING);
            paymentEntry.setPayeeId(payment.getPaymentBooking().getBooking().getId());
            paymentEntry.setPayeeName(payment.getPaymentBooking().getBooking().getClient().getPocName());
        }

        if (payment.getPaymentStudentActivity() != null) {
            paymentEntry.setPayeeType(PayeeType.STUDENT);
            paymentEntry.setPayeeId(payment.getPaymentStudentActivity().getStudentActivityAssignment().getId());
            paymentEntry.setPayeeName(
                    payment.getPaymentStudentActivity().getStudentActivityAssignment().getStudent().getName());
            paymentEntry.setActualAmount(payment.getPaymentStudentActivity().getActualAmount());
        }
        return paymentEntry;
    }

    private static void convertToEntry(Payment payment, PaymentEntry paymentEntry) {
        paymentEntry.setId(payment.getId());
        paymentEntry.setBranchId(payment.getBranch().getId());
        paymentEntry.setAmount(payment.getAmount());
        paymentEntry.setPaymentDate(payment.getPaymentDate());
        paymentEntry.setStatus(payment.getStatus());
        paymentEntry.setPaymentType(payment.getPaymentType());
        paymentEntry.setTransactionType(payment.getTransactionType());
    }

    public static PaymentBooking convertToEntity(PaymentEntry paymentEntry, PaymentBooking existingBookingPayment) {
        PaymentBooking paymentBooking = (existingBookingPayment != null) ? existingBookingPayment
                : new PaymentBooking();
        if (existingBookingPayment == null) {
            paymentBooking.setId(null);
        }
        if (Objects.nonNull(paymentEntry.getPayeeId())) {
            BookingRepository repository = applicationContext
                    .getBean(BookingRepository.class);
            Booking booking = repository.getReferenceById(paymentEntry.getPayeeId());
            paymentBooking.setBooking(booking);
        }
        paymentBooking.setPayment(convertToEntity(paymentEntry, paymentBooking.getPayment()));
        return paymentBooking;
    }

    public static PaymentStudentActivity convertToEntity(PaymentEntry paymentEntry,
            PaymentStudentActivity existingStudentActivityPayment) {
        PaymentStudentActivity studentActivityPayment = (existingStudentActivityPayment != null)
                ? existingStudentActivityPayment
                : new PaymentStudentActivity();
        if (existingStudentActivityPayment == null) {
            studentActivityPayment.setId(null);
        }
        if (Objects.nonNull(paymentEntry.getActualAmount())) {
            studentActivityPayment.setActualAmount(paymentEntry.getActualAmount());
        }
        if (Objects.nonNull(paymentEntry.getPayeeId())) {
            StudentActivityAssignmentRepository repository = applicationContext
                    .getBean(StudentActivityAssignmentRepository.class);
            StudentActivityAssignment assignment = repository.getReferenceById(paymentEntry.getPayeeId());
            studentActivityPayment.setStudentActivityAssignment(assignment);
        }
        studentActivityPayment.setPayment(convertToEntity(paymentEntry, studentActivityPayment.getPayment()));
        return studentActivityPayment;
    }

    private static Payment convertToEntity(PaymentEntry paymentEntry, Payment existingPayment) {
        Payment payment = (existingPayment != null) ? existingPayment : new Payment();
        if (existingPayment != null && Objects.nonNull(paymentEntry.getId()) && paymentEntry.getId() > 0) {
            payment.setId(Long.valueOf(paymentEntry.getId()));
        } else if (existingPayment == null) {
            payment.setId(null);
        }
        if (Objects.nonNull(paymentEntry.getBranchId())) {
            BranchRepository branchRepository = applicationContext.getBean(BranchRepository.class);
            Branch branch = branchRepository.findById(paymentEntry.getBranchId())
                    .orElseThrow(() -> new EntityNotFoundException("Branch not found"));
            payment.setBranch(branch);
        }
        if (Objects.nonNull(paymentEntry.getAmount())) {
            payment.setAmount(paymentEntry.getAmount());
        }
        if (Objects.nonNull(paymentEntry.getPaymentDate())) {
            payment.setPaymentDate(paymentEntry.getPaymentDate());
        }
        if (Objects.nonNull(paymentEntry.getStatus())) {
            payment.setStatus(paymentEntry.getStatus());
        }
        if (Objects.nonNull(paymentEntry.getPaymentType())) {
            payment.setPaymentType(paymentEntry.getPaymentType());
        }
        if (Objects.nonNull(paymentEntry.getTransactionType())) {
            payment.setTransactionType(paymentEntry.getTransactionType());
        }
        return payment;
    }
}
