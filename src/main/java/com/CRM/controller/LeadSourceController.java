package com.CRM.controller;

import com.CRM.dto.request.LeadSourceRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.LeadSourceResponse;
import com.CRM.service.LeadSourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lead-sources")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeadSourceController {

    private final LeadSourceService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<LeadSourceResponse>> create(
            @Valid @RequestBody LeadSourceRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LeadSourceResponse>builder()
                        .success(true)
                        .message("Lead source created successfully.")
                        .data(service.save(request))
                        .build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadSourceResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadSourceResponse>>builder()
                        .success(true)
                        .message("Lead sources fetched successfully.")
                        .data(service.findAll())
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LeadSourceResponse>> getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<LeadSourceResponse>builder()
                        .success(true)
                        .message("Lead source fetched successfully.")
                        .data(service.findById(id))
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<LeadSourceResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody LeadSourceRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<LeadSourceResponse>builder()
                        .success(true)
                        .message("Lead source updated successfully.")
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