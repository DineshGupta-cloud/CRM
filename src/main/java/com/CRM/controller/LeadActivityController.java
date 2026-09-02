package com.CRM.controller;

import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.LeadActivityResponse;
import com.CRM.service.LeadActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lead-activities")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeadActivityController {

    private final LeadActivityService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadActivityResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadActivityResponse>>builder()
                        .success(true)
                        .message("Lead activities fetched successfully.")
                        .data(service.findAll())
                        .build()
        );
    }

    @GetMapping("/lead/{leadId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadActivityResponse>>> getByLead(
            @PathVariable Long leadId) {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadActivityResponse>>builder()
                        .success(true)
                        .message("Lead timeline fetched successfully.")
                        .data(service.findByLead(leadId))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Lead activity deleted successfully.")
                        .build()
        );
    }

    @GetMapping("/next-number")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> getNextNumber() {

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Next activity number generated successfully.")
                        .data(service.getNextActivityNumber())
                        .build()
        );
    }
}