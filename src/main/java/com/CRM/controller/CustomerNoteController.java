package com.CRM.controller;

import com.CRM.dto.request.CustomerNoteRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.CustomerNoteResponse;
import com.CRM.service.CustomerNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerNoteController {

    private final CustomerNoteService customerNoteService;

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerNoteResponse>> create(
            @Valid @RequestBody CustomerNoteRequest request) {

        CustomerNoteResponse response = customerNoteService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerNoteResponse>builder()
                        .success(true)
                        .message("Customer note created successfully.")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerNoteResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerNoteRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerNoteResponse>builder()
                        .success(true)
                        .message("Customer note updated successfully.")
                        .data(customerNoteService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerNoteResponse>>> getByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerNoteResponse>>builder()
                        .success(true)
                        .message("Customer notes fetched successfully.")
                        .data(customerNoteService.getByCustomer(customerId))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        customerNoteService.delete(id);

        return ResponseEntity.noContent().build();
    }
}