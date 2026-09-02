package com.CRM.controller;

import com.CRM.dto.request.CustomerContactRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.CustomerContactResponse;
import com.CRM.service.CustomerContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-contacts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerContactController {

    private final CustomerContactService customerContactService;

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerContactResponse>> create(
            @Valid @RequestBody CustomerContactRequest request) {

        CustomerContactResponse response = customerContactService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerContactResponse>builder()
                        .success(true)
                        .message("Customer contact created successfully.")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerContactResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerContactRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerContactResponse>builder()
                        .success(true)
                        .message("Customer contact updated successfully.")
                        .data(customerContactService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerContactResponse>>> getByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerContactResponse>>builder()
                        .success(true)
                        .message("Customer contacts fetched successfully.")
                        .data(customerContactService.getByCustomer(customerId))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        customerContactService.delete(id);

        return ResponseEntity.noContent().build();
    }
}