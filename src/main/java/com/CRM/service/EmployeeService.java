package com.CRM.service;

import com.CRM.dto.request.EmployeeRequest;
import com.CRM.dto.response.EmployeeResponse;
import com.CRM.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    Page<EmployeeResponse> getEmployees(Pageable pageable);

    void deleteEmployee(Long id);

    String generateEmployeeCode();

}

