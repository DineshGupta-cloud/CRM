package com.CRM.controller;

import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.PipelineStageSummaryResponse;
import com.CRM.service.PipelineDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard/pipeline")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PipelineDashboardController {

    private final PipelineDashboardService service;

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PipelineStageSummaryResponse>>> getPipeline() {

        return ResponseEntity.ok(
                ApiResponse.<List<PipelineStageSummaryResponse>>builder()
                        .success(true)
                        .message("Pipeline dashboard fetched successfully.")
                        .data(service.getPipelineSummary())
                        .build()
        );
    }
}