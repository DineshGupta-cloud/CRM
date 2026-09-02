package com.CRM.controller;

import com.CRM.dto.request.LeadActivityTypeRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.LeadActivityTypeResponse;
import com.CRM.service.LeadActivityTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lead-activity-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeadActivityTypeController {

    private final LeadActivityTypeService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LeadActivityTypeResponse>> create(
            @Valid @RequestBody LeadActivityTypeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LeadActivityTypeResponse>builder()
                        .success(true)
                        .message("Activity type created successfully.")
                        .data(service.save(request))
                        .build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadActivityTypeResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadActivityTypeResponse>>builder()
                        .success(true)
                        .message("Activity types fetched successfully.")
                        .data(service.findAll())
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LeadActivityTypeResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<LeadActivityTypeResponse>builder()
                        .success(true)
                        .message("Activity type fetched successfully.")
                        .data(service.findById(id))
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LeadActivityTypeResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody LeadActivityTypeRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<LeadActivityTypeResponse>builder()
                        .success(true)
                        .message("Activity type updated successfully.")
                        .data(service.update(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Activity type deleted successfully.")
                        .build());
    }

    @GetMapping("/next-code")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> getNextCode() {

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Next activity code generated successfully.")
                        .data(service.getNextActivityCode())
                        .build());
    }
}