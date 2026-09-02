package com.CRM.controller;

import com.CRM.dto.request.OpportunityProductRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.OpportunityProductResponse;
import com.CRM.service.OpportunityProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunity-products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OpportunityProductController {

    private final OpportunityProductService service;

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PostMapping
    public ResponseEntity<ApiResponse<OpportunityProductResponse>> add(
            @Valid @RequestBody OpportunityProductRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<OpportunityProductResponse>builder()
                        .success(true)
                        .message("Opportunity product added successfully.")
                        .data(service.add(request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OpportunityProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody OpportunityProductRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<OpportunityProductResponse>builder()
                        .success(true)
                        .message("Opportunity product updated successfully.")
                        .data(service.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/opportunity/{opportunityId}")
    public ResponseEntity<ApiResponse<List<OpportunityProductResponse>>> getByOpportunity(
            @PathVariable Long opportunityId) {

        return ResponseEntity.ok(
                ApiResponse.<List<OpportunityProductResponse>>builder()
                        .success(true)
                        .message("Opportunity products fetched successfully.")
                        .data(service.getByOpportunity(opportunityId))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}