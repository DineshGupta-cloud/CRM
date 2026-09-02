package com.CRM.controller;

import com.CRM.dto.request.OpportunityActivityRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.OpportunityActivityResponse;
import com.CRM.service.OpportunityActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunity-activities")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OpportunityActivityController {

    private final OpportunityActivityService opportunityActivityService;

//    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
//    @PostMapping
//    public ResponseEntity<ApiResponse<OpportunityActivityResponse>> create(
//            @Valid @RequestBody OpportunityActivityRequest request) {
//
//        OpportunityActivityResponse response =
//                opportunityActivityService.logActivity(request);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(ApiResponse.<OpportunityActivityResponse>builder()
//                        .success(true)
//                        .message("Opportunity activity logged successfully.")
//                        .data(response)
//                        .build());
//    }

//    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
//    @GetMapping("/opportunity/{opportunityId}")
//    public ResponseEntity<ApiResponse<List<OpportunityActivityResponse>>> getByOpportunity(
//            @PathVariable Long opportunityId) {
//
//        return ResponseEntity.ok(
//                ApiResponse.<List<OpportunityActivityResponse>>builder()
//                        .success(true)
//                        .message("Opportunity activities fetched successfully.")
//                        .data(opportunityActivityService.getByOpportunity(opportunityId))
//                        .build());
//    }
}