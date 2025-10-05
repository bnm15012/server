package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.EnquiryEntry;
import com.dancestudio.erp.response.EnquiryResponse;
import com.dancestudio.erp.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enquiries")
public class EnquiryController extends BaseController<EnquiryEntry, EnquiryResponse, Long> {

    @Autowired
    private EnquiryService enquiryService;

    @Override
    public ResponseEntity<EnquiryResponse> add(@RequestBody EnquiryEntry enquiryEntry) {
        return enquiryService.add(enquiryEntry);
    }

    @Override
    public ResponseEntity<EnquiryResponse> update(@PathVariable Long id, @RequestBody EnquiryEntry enquiryEntry) {
        return enquiryService.update(id, enquiryEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return enquiryService.delete(id);
    }

    @Override
    public ResponseEntity<EnquiryResponse> get(@PathVariable Long id) {
        return enquiryService.get(id);
    }

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<EnquiryResponse> getAllEnquiries(@PathVariable Long branchId,
                                                           @RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size,
                                                           @RequestParam(required = false) Integer startMonth,
                                                           @RequestParam(required = false) Integer startYear,
                                                           @RequestParam(required = false) Integer endMonth,
                                                           @RequestParam(required = false) Integer endYear,
                                                           @RequestParam(required = false) String searchTerm) {
        return enquiryService.getAllEnquiries(branchId, page, size,
                startMonth, startYear, endMonth, endYear, searchTerm);
    }
}
