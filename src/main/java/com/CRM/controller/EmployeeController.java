package com.CRM.controller;

import com.CRM.dto.request.EmployeeRequest;
import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.EmployeeResponse;
import com.CRM.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = employeeService.createEmployee(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee created successfully.")
                        .data(response)
                        .build());
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployee(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee fetched successfully.")
                        .data(employeeService.getEmployeeById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {

        return ResponseEntity.ok(
                ApiResponse.<List<EmployeeResponse>>builder()
                        .success(true)
                        .message("Employees fetched successfully.")
                        .data(employeeService.getAllEmployees())
                        .build());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(Pageable pageable) {

        return ResponseEntity.ok(
                employeeService.getEmployees(pageable)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee updated successfully.")
                        .data(employeeService.updateEmployee(id, request))
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/next-code")
    public ResponseEntity<String> nextCode() {

        return ResponseEntity.ok(employeeService.generateEmployeeCode());
    }
}