package com.dancestudio.erp.modules.booking;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController extends BaseController<BookingEntry, Long> {

    @Autowired
    private BookingService bookingService;

    @Override
    protected BaseService<BookingEntry, Long> getService() {
        return bookingService;
    }

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<BaseResponse<BookingEntry>> getAllBookings(@PathVariable Long branchId,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") Integer startMonth,
            @RequestParam(defaultValue = "0") Integer startYear,
            @RequestParam(defaultValue = "0") Integer endMonth,
            @RequestParam(defaultValue = "0") Integer endYear,
            @RequestParam(required = false) String searchTerm) {
        return bookingService.getAllBookings(branchId, page, size, startMonth, startYear, endMonth, endYear,
                searchTerm);
    }
}
