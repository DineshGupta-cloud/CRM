package com.CRM.controller;

import com.CRM.dto.request.CompanyRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.CompanyResponse;
import com.CRM.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@PreAuthorize("hasRole('ADMIN')")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> create(
            @Valid @RequestBody CompanyRequest request) {

        CompanyResponse response = companyService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CompanyResponse>builder()
                        .success(true)
                        .message("Company created successfully.")
                        .data(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<CompanyResponse>>builder()
                        .success(true)
                        .message("Companies fetched successfully.")
                        .data(companyService.getAll())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<CompanyResponse>builder()
                        .success(true)
                        .message("Company fetched successfully.")
                        .data(companyService.getById(id))
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<CompanyResponse>builder()
                        .success(true)
                        .message("Company updated successfully.")
                        .data(companyService.update(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        companyService.delete(id);

        return ResponseEntity.noContent().build();
    }
}