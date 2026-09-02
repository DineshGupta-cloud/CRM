package com.CRM.controller;

import com.CRM.dto.request.LeadStatusRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.LeadStatusResponse;
import com.CRM.service.LeadStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lead-status")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeadStatusController {

    private final LeadStatusService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<LeadStatusResponse>> create(
            @Valid @RequestBody LeadStatusRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LeadStatusResponse>builder()
                        .success(true)
                        .message("Lead status created successfully.")
                        .data(service.save(request))
                        .build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadStatusResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadStatusResponse>>builder()
                        .success(true)
                        .message("Lead statuses fetched successfully.")
                        .data(service.findAll())
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LeadStatusResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<LeadStatusResponse>builder()
                        .success(true)
                        .message("Lead status fetched successfully.")
                        .data(service.findById(id))
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<LeadStatusResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody LeadStatusRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<LeadStatusResponse>builder()
                        .success(true)
                        .message("Lead status updated successfully.")
                        .data(service.update(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}