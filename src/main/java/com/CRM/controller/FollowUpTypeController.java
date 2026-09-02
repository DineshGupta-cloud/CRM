package com.CRM.controller;

import com.CRM.dto.request.FollowUpTypeRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.FollowUpTypeResponse;
import com.CRM.service.FollowUpTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow-up-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FollowUpTypeController {

    private final FollowUpTypeService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<FollowUpTypeResponse>> create(
            @Valid @RequestBody FollowUpTypeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FollowUpTypeResponse>builder()
                        .success(true)
                        .message("Follow-up type created successfully.")
                        .data(service.save(request))
                        .build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<FollowUpTypeResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<FollowUpTypeResponse>>builder()
                        .success(true)
                        .message("Follow-up types fetched successfully.")
                        .data(service.findAll())
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<FollowUpTypeResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<FollowUpTypeResponse>builder()
                        .success(true)
                        .message("Follow-up type fetched successfully.")
                        .data(service.findById(id))
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<FollowUpTypeResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody FollowUpTypeRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<FollowUpTypeResponse>builder()
                        .success(true)
                        .message("Follow-up type updated successfully.")
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