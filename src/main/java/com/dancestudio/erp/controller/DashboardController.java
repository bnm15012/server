package com.dancestudio.erp.controller;

import com.dancestudio.erp.response.DashboardResponse;
import com.dancestudio.erp.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/getDashboardDetails/{studioId}")
    public DashboardResponse getDashboardDetails(@PathVariable Long studioId, @RequestParam(defaultValue = "0") int currentMonth, @RequestParam(defaultValue = "0") int currentYear) {
        return dashboardService.getDashboardDetails(studioId, currentMonth, currentYear);
    }
}
