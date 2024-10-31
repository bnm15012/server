package com.dancestudio.erp.controller;

import com.dancestudio.erp.response.DashboardResponse;
import com.dancestudio.erp.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/getDashboardDetails/{studioId}")
    public DashboardResponse getDashboardDetails(@PathVariable Long studioId) {
        return dashboardService.getDashboardDetails(studioId);
    }
}
