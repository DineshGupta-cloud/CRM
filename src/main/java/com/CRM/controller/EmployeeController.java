package com.CRM.controller;

import com.CRM.dto.response.ApiResponse;
import com.CRM.dto.response.EmployeeResponse;
import com.CRM.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER','TEAM_LEAD','EMPLOYEE')")
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
}
