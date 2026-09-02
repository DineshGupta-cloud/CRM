package com.CRM.controller;

import com.CRM.dto.request.RoleRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.RoleResponse;
import com.CRM.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> create(
            @Valid @RequestBody RoleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .message("Role created successfully.")
                        .data(roleService.create(request))
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<RoleResponse>>builder()
                        .success(true)
                        .message("Roles fetched successfully.")
                        .data(roleService.getAll())
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .message("Role fetched successfully.")
                        .data(roleService.getById(id))
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .message("Role updated successfully.")
                        .data(roleService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        roleService.delete(id);

        return ResponseEntity.noContent().build();
    }
}