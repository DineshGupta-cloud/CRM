package com.CRM.controller;

import com.CRM.dto.request.BranchRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.BranchResponse;
import com.CRM.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BranchController {

    private final BranchService branchService;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PostMapping
    public ResponseEntity<ApiResponse<BranchResponse>> create(
            @Valid @RequestBody BranchRequest request) {

        BranchResponse response = branchService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<BranchResponse>builder()
                        .success(true)
                        .message("Branch created successfully.")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BranchResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<BranchResponse>>builder()
                        .success(true)
                        .message("Branches fetched successfully.")
                        .data(branchService.getAll())
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BranchResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<BranchResponse>builder()
                        .success(true)
                        .message("Branch fetched successfully.")
                        .data(branchService.getById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BranchResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody BranchRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<BranchResponse>builder()
                        .success(true)
                        .message("Branch updated successfully.")
                        .data(branchService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        branchService.delete(id);

        return ResponseEntity.noContent().build();
    }
}