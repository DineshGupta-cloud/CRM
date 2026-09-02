package com.CRM.controller;

import com.CRM.dto.request.CustomerRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.CustomerResponse;
import com.CRM.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> create(
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse response = customerService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer created successfully.")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerResponse>>builder()
                        .success(true)
                        .message("Customers fetched successfully.")
                        .data(customerService.getAll())
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer fetched successfully.")
                        .data(customerService.getById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer updated successfully.")
                        .data(customerService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        customerService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PostMapping("/convert/{leadId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> convertLead(
            @PathVariable Long leadId) {

        CustomerResponse response = customerService.convertLead(leadId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Lead converted successfully.")
                        .data(response)
                        .build());
    }

}