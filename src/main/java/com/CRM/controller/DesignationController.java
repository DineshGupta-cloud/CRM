package com.CRM.controller;

import com.CRM.dto.request.DesignationRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.DesignationResponse;
import com.CRM.service.DesignationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DesignationController {

    private final DesignationService designationService;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PostMapping
    public ResponseEntity<ApiResponse<DesignationResponse>> create(
            @Valid @RequestBody DesignationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DesignationResponse>builder()
                        .success(true)
                        .message("Designation created successfully.")
                        .data(designationService.create(request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DesignationResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<DesignationResponse>>builder()
                        .success(true)
                        .message("Designations fetched successfully.")
                        .data(designationService.getAll())
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DesignationResponse>> getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<DesignationResponse>builder()
                        .success(true)
                        .message("Designation fetched successfully.")
                        .data(designationService.getById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DesignationResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DesignationRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<DesignationResponse>builder()
                        .success(true)
                        .message("Designation updated successfully.")
                        .data(designationService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        designationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}