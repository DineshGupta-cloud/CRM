package com.CRM.controller;

import com.CRM.dto.request.LeadNoteRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.LeadNoteResponse;
import com.CRM.service.LeadNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lead-notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeadNoteController {

    private final LeadNoteService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER','SALES')")
    public ResponseEntity<ApiResponse<LeadNoteResponse>> create(
            @Valid @RequestBody LeadNoteRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<LeadNoteResponse>builder()
                                .success(true)
                                .message("Lead note created successfully.")
                                .data(service.save(request))
                                .build()
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER','SALES')")
    public ResponseEntity<ApiResponse<LeadNoteResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody LeadNoteRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<LeadNoteResponse>builder()
                        .success(true)
                        .message("Lead note updated successfully.")
                        .data(service.update(id, request))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadNoteResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadNoteResponse>>builder()
                        .success(true)
                        .message("Lead notes fetched successfully.")
                        .data(service.findAll())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LeadNoteResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<LeadNoteResponse>builder()
                        .success(true)
                        .message("Lead note fetched successfully.")
                        .data(service.findById(id))
                        .build()
        );
    }

    @GetMapping("/lead/{leadId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<LeadNoteResponse>>> getByLead(
            @PathVariable Long leadId) {

        return ResponseEntity.ok(
                ApiResponse.<List<LeadNoteResponse>>builder()
                        .success(true)
                        .message("Lead notes fetched successfully.")
                        .data(service.findByLead(leadId))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Lead note deleted successfully.")
                        .build()
        );
    }

    @GetMapping("/next-code")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> getNextCode() {

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Next lead note code generated successfully.")
                        .data(service.getNextNoteCode())
                        .build()
        );
    }
}