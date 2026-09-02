package com.CRM.controller;

import com.CRM.dto.request.OpportunityRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.OpportunityResponse;
import com.CRM.service.OpportunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OpportunityController {

    private final OpportunityService opportunityService;

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PostMapping
    public ResponseEntity<ApiResponse<OpportunityResponse>> create(
            @Valid @RequestBody OpportunityRequest request) {

        OpportunityResponse response = opportunityService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<OpportunityResponse>builder()
                        .success(true)
                        .message("Opportunity created successfully.")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OpportunityResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<OpportunityResponse>>builder()
                        .success(true)
                        .message("Opportunities fetched successfully.")
                        .data(opportunityService.getAll())
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OpportunityResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<OpportunityResponse>builder()
                        .success(true)
                        .message("Opportunity fetched successfully.")
                        .data(opportunityService.getById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<OpportunityResponse>>> getByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                ApiResponse.<List<OpportunityResponse>>builder()
                        .success(true)
                        .message("Customer opportunities fetched successfully.")
                        .data(opportunityService.getByCustomer(customerId))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OpportunityResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody OpportunityRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<OpportunityResponse>builder()
                        .success(true)
                        .message("Opportunity updated successfully.")
                        .data(opportunityService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        opportunityService.delete(id);

        return ResponseEntity.noContent().build();
    }
}