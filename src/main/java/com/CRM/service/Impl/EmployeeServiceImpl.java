package com.CRM.service.Impl;

import com.CRM.dto.request.EmployeeRequest;
import com.CRM.dto.response.EmployeeResponse;
import com.CRM.entity.*;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.EmployeeMapper;
import com.CRM.repository.*;
import com.CRM.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final RoleRepository roleRepository;
    private final EmployeeMapper mapper;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));


        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Designation designation = designationRepository.findById(request.getDesignationId())
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Employee manager = null;

        if (request.getReportingManagerId() != null) {
            manager = employeeRepository.findById(request.getReportingManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
        }

        Employee employee = mapper.toEntity(
                request,
                company,
                branch,
                department,
                designation,
                role,
                manager
        );

        employee.setEmployeeCode(generateEmployeeCode());

        employee = employeeRepository.save(employee);

        return mapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        if (!employee.getEmail().equals(request.getEmail())
                && employeeRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists.");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        Designation designation = designationRepository.findById(request.getDesignationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Designation not found"));

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found"));

        Employee manager = null;

        if (request.getReportingManagerId() != null) {

            manager = employeeRepository.findById(request.getReportingManagerId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Reporting Manager not found"));
        }

        mapper.updateEntity(
                employee,
                request,
                company,
                branch,
                department,
                designation,
                role,
                manager
        );

        employee = employeeRepository.save(employee);

        return mapper.toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        return mapper.toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getEmployees(Pageable pageable) {

        return employeeRepository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        employee.setDeleted(true);
        employee.setActive(false);

        employeeRepository.save(employee);
    }

    @Override
    public String generateEmployeeCode() {

        long count = employeeRepository.count() + 1;

        return String.format("EMP%05d", count);
    }
}