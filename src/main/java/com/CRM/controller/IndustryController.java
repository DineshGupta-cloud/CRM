package com.CRM.controller;

import com.CRM.dto.request.IndustryRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.IndustryResponse;
import com.CRM.service.IndustryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/industries")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class IndustryController {

    private final IndustryService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<IndustryResponse>> create(
            @Valid @RequestBody IndustryRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<IndustryResponse>builder()
                        .success(true)
                        .message("Industry created successfully.")
                        .data(service.save(request))
                        .build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<IndustryResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<IndustryResponse>>builder()
                        .success(true)
                        .message("Industries fetched successfully.")
                        .data(service.findAll())
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<IndustryResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<IndustryResponse>builder()
                        .success(true)
                        .message("Industry fetched successfully.")
                        .data(service.findById(id))
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<IndustryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody IndustryRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<IndustryResponse>builder()
                        .success(true)
                        .message("Industry updated successfully.")
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