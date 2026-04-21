package com.dancestudio.erp.modules.booking;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.response.StatusResponse;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class BookingService extends BaseService<BookingEntry, Long> {

    private BookingManager bookingManager;

    public ResponseEntity<BaseResponse<BookingEntry>> getAllBookings(Long branchId, Integer page, Integer size,
            Integer startMonth,
            Integer startYear, Integer endMonth, Integer endYear, String searchTerm) {
        BaseResponse<BookingEntry> response = new BaseResponse<>();
        try {
            Page<BookingEntry> entries = bookingManager.getAllBookings(branchId, --page, size, null, startMonth,
                    startYear, null, endMonth, endYear, searchTerm);
            response.setData(entries.getContent());
            response.setStatus(new StatusResponse(1, "Bookings retrieved successfully", StatusResponse.Type.SUCCESS,
                    entries.getTotalElements()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    protected BookingEntry doAdd(BookingEntry entry) throws Exception {
        return bookingManager.add(entry);
    }

    @Override
    protected BookingEntry doUpdate(Long id, BookingEntry entry) throws Exception {
        return bookingManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        bookingManager.delete(id);
    }

    @Override
    protected BookingEntry doGet(Long id) throws Exception {
        return bookingManager.getById(id);
    }
}
