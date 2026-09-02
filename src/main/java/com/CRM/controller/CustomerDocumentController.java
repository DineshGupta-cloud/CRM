package com.CRM.controller;

import com.CRM.dto.request.CustomerDocumentRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.CustomerDocumentResponse;
import com.CRM.service.CustomerDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerDocumentController {

    private final CustomerDocumentService customerDocumentService;

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDocumentResponse>> create(
            @Valid @RequestBody CustomerDocumentRequest request) {

        CustomerDocumentResponse response = customerDocumentService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerDocumentResponse>builder()
                        .success(true)
                        .message("Customer document created successfully.")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDocumentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDocumentRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerDocumentResponse>builder()
                        .success(true)
                        .message("Customer document updated successfully.")
                        .data(customerDocumentService.update(id, request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDocumentResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerDocumentResponse>builder()
                        .success(true)
                        .message("Customer document fetched successfully.")
                        .data(customerDocumentService.getById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerDocumentResponse>>> getByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerDocumentResponse>>builder()
                        .success(true)
                        .message("Customer documents fetched successfully.")
                        .data(customerDocumentService.getByCustomer(customerId))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','SALES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        customerDocumentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}