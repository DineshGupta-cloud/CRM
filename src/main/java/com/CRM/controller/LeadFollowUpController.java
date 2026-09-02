package com.CRM.controller;

import com.CRM.dto.request.LeadFollowUpRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.LeadFollowUpResponse;
import com.CRM.service.LeadFollowUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lead-followups")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeadFollowUpController {

    private final LeadFollowUpService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER','SALES')")
    public ResponseEntity<ApiResponse<LeadFollowUpResponse>> create(
            @Valid @RequestBody LeadFollowUpRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LeadFollowUpResponse>builder()
                        .success(true)
                        .message("Follow-up created successfully.")
                        .data(service.save(request))
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER','SALES')")
    public ResponseEntity<ApiResponse<LeadFollowUpResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody LeadFollowUpRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<LeadFollowUpResponse>builder()
                        .success(true)
                        .message("Follow-up updated successfully.")
                        .data(service.update(id, request))
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LeadFollowUpResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<LeadFollowUpResponse>builder()
                        .success(true)
                        .message("Follow-up fetched successfully.")
                        .data(service.findById(id))
                        .build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadFollowUpResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadFollowUpResponse>>builder()
                        .success(true)
                        .message("Follow-ups fetched successfully.")
                        .data(service.findAll())
                        .build());
    }

    @GetMapping("/lead/{leadId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadFollowUpResponse>>> getLeadFollowUps(
            @PathVariable Long leadId) {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadFollowUpResponse>>builder()
                        .success(true)
                        .message("Lead follow-ups fetched successfully.")
                        .data(service.findByLead(leadId))
                        .build());
    }

    @GetMapping("/today")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadFollowUpResponse>>> getTodayFollowUps() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadFollowUpResponse>>builder()
                        .success(true)
                        .message("Today's follow-ups fetched successfully.")
                        .data(service.getTodayFollowUps())
                        .build());
    }

    @GetMapping("/overdue")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadFollowUpResponse>>> getOverdueFollowUps() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadFollowUpResponse>>builder()
                        .success(true)
                        .message("Overdue follow-ups fetched successfully.")
                        .data(service.getOverdueFollowUps())
                        .build());
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadFollowUpResponse>>> getEmployeeFollowUps(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadFollowUpResponse>>builder()
                        .success(true)
                        .message("Employee follow-ups fetched successfully.")
                        .data(service.getEmployeeFollowUps(employeeId))
                        .build());
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER','SALES')")
    public ResponseEntity<ApiResponse<LeadFollowUpResponse>> complete(
            @PathVariable Long id,
            @RequestParam String outcome) {

        return ResponseEntity.ok(
                ApiResponse.<LeadFollowUpResponse>builder()
                        .success(true)
                        .message("Follow-up completed successfully.")
                        .data(service.complete(id, outcome))
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Follow-up deleted successfully.")
                        .build());
    }

    @GetMapping("/next-code")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> getNextCode() {

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Next follow-up code generated successfully.")
                        .data(service.getNextFollowUpCode())
                        .build());
    }
}