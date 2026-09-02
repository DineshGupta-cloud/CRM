package com.CRM.controller;

import com.CRM.dto.request.OpportunityStageRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.OpportunityStageResponse;
import com.CRM.service.OpportunityStageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunity-stages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OpportunityStageController {

    private final OpportunityStageService service;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PostMapping
    public ResponseEntity<ApiResponse<OpportunityStageResponse>> create(
            @Valid @RequestBody OpportunityStageRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<OpportunityStageResponse>builder()
                        .success(true)
                        .message("Opportunity stage created successfully.")
                        .data(service.create(request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OpportunityStageResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<OpportunityStageResponse>>builder()
                        .success(true)
                        .message("Opportunity stages fetched successfully.")
                        .data(service.getAll())
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OpportunityStageResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<OpportunityStageResponse>builder()
                        .success(true)
                        .message("Opportunity stage fetched successfully.")
                        .data(service.getById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OpportunityStageResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody OpportunityStageRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<OpportunityStageResponse>builder()
                        .success(true)
                        .message("Opportunity stage updated successfully.")
                        .data(service.update(id, request))
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}