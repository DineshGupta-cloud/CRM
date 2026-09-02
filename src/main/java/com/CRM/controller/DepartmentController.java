package com.CRM.controller;

import com.CRM.dto.request.DepartmentRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.DepartmentResponse;
import com.CRM.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> create(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response = departmentService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DepartmentResponse>builder()
                        .success(true)
                        .message("Department created successfully.")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<DepartmentResponse>>builder()
                        .success(true)
                        .message("Departments fetched successfully.")
                        .data(departmentService.getAll())
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<DepartmentResponse>builder()
                        .success(true)
                        .message("Department fetched successfully.")
                        .data(departmentService.getById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<DepartmentResponse>builder()
                        .success(true)
                        .message("Department updated successfully.")
                        .data(departmentService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        departmentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}