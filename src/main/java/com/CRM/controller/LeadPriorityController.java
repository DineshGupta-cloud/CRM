package com.CRM.controller;

import com.CRM.dto.request.LeadPriorityRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.LeadPriorityResponse;
import com.CRM.service.LeadPriorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lead-priority")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeadPriorityController {

    private final LeadPriorityService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<LeadPriorityResponse>> create(
            @Valid @RequestBody LeadPriorityRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LeadPriorityResponse>builder()
                        .success(true)
                        .message("Lead priority created successfully.")
                        .data(service.save(request))
                        .build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadPriorityResponse>>> getAll() {

        return ResponseEntity.ok(ApiResponse.<List<LeadPriorityResponse>>builder()
                .success(true)
                .message("Lead priorities fetched successfully.")
                .data(service.findAll())
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LeadPriorityResponse>> getById(@PathVariable Long id) {

        return ResponseEntity.ok(ApiResponse.<LeadPriorityResponse>builder()
                .success(true)
                .message("Lead priority fetched successfully.")
                .data(service.findById(id))
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<LeadPriorityResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody LeadPriorityRequest request) {

        return ResponseEntity.ok(ApiResponse.<LeadPriorityResponse>builder()
                .success(true)
                .message("Lead priority updated successfully.")
                .data(service.update(id, request))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}