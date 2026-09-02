package com.CRM.controller;

import com.CRM.dto.dashboard.DashboardResponse;
import com.CRM.dto.response.ApiResponse;
import com.CRM.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    public ResponseEntity<ApiResponse<DashboardResponse>> dashboard() {

        return ResponseEntity.ok(

                ApiResponse.<DashboardResponse>builder()
                        .success(true)
                        .message("Dashboard loaded successfully.")
                        .data(dashboardService.getDashboard())
                        .build()

        );
    }
}