package com.CRM.controller;

import com.CRM.dto.DashboardSummaryDto;
import com.CRM.dto.EmployeeDto;
import com.CRM.entity.Employee;
import com.CRM.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/dashboard")
@RestController
public class Dashboard {

    private DashboardService dashboardService;

    public Dashboard(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getSummary() {

        return ResponseEntity.ok(
                dashboardService.getDashboardDetails());
    }

    @GetMapping("/topEmp")
    public List<EmployeeDto> getTopEmployee() {

        return  dashboardService.findTop5ByOrderByIdDesc();
    }
}
