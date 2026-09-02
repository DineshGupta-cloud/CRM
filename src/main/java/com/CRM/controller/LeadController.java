package com.CRM.controller;

import com.CRM.dto.request.LeadRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.LeadResponse;
import com.CRM.service.LeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeadController {

    private final LeadService leadService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER','SALES')")
    public ResponseEntity<ApiResponse<LeadResponse>> createLead(
            @Valid @RequestBody LeadRequest request) {

        LeadResponse response = leadService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LeadResponse>builder()
                        .success(true)
                        .message("Lead created successfully.")
                        .data(response)
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER','SALES')")
    public ResponseEntity<ApiResponse<LeadResponse>> updateLead(
            @PathVariable Long id,
            @Valid @RequestBody LeadRequest request) {

        LeadResponse response = leadService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.<LeadResponse>builder()
                        .success(true)
                        .message("Lead updated successfully.")
                        .data(response)
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LeadResponse>> getLeadById(
            @PathVariable Long id) {

        LeadResponse response = leadService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.<LeadResponse>builder()
                        .success(true)
                        .message("Lead fetched successfully.")
                        .data(response)
                        .build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadResponse>>> getAllLeads() {

        List<LeadResponse> response = leadService.findAll();

        return ResponseEntity.ok(
                ApiResponse.<List<LeadResponse>>builder()
                        .success(true)
                        .message("Leads fetched successfully.")
                        .data(response)
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<Void>> deleteLead(
            @PathVariable Long id) {

        leadService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Lead deleted successfully.")
                        .build());
    }

    @GetMapping("/next-code")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> getNextLeadCode() {

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Next lead code generated successfully.")
                        .data(leadService.getNextLeadCode())
                        .build());
    }
}