package com.CRM.controller;

import com.CRM.dto.request.CustomerAddressRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.CustomerAddressResponse;
import com.CRM.service.CustomerAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerAddressResponse>> create(
            @Valid @RequestBody CustomerAddressRequest request) {

        CustomerAddressResponse response = customerAddressService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerAddressResponse>builder()
                        .success(true)
                        .message("Customer address created successfully.")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerAddressResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerAddressRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerAddressResponse>builder()
                        .success(true)
                        .message("Customer address updated successfully.")
                        .data(customerAddressService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerAddressResponse>>> getByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerAddressResponse>>builder()
                        .success(true)
                        .message("Customer addresses fetched successfully.")
                        .data(customerAddressService.getByCustomer(customerId))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        customerAddressService.delete(id);

        return ResponseEntity.noContent().build();
    }
}