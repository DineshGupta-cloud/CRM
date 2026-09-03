package com.CRM.service.Impl;

import com.CRM.dto.request.EmployeeRequest;
import com.CRM.dto.response.EmployeeResponse;
import com.CRM.entity.*;
import com.CRM.exception.ForbiddenException;
import com.CRM.security.SecurityUtils;
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
    private final EmployeeMapper mapper;
    private final SecurityUtils securityUtils;
    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Long companyId;

        if (securityUtils.isAdmin()) {
            companyId = request.getCompanyId();
        } else {
            companyId = securityUtils.getCurrentCompanyId();

            if (!companyId.equals(request.getCompanyId())) {
                throw new ForbiddenException(
                        "You are not allowed to create an employee for another company."
                );
            }
        }

        Company company = companyRepository.findByIdAndDeletedFalse(companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        Branch branch = branchRepository
                .findByIdAndCompanyIdAndDeletedFalse(
                        request.getBranchId(),
                        companyId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Branch not found in the selected company"));

        Department department = departmentRepository
                .findByIdAndBranchIdAndDeletedFalse(
                        request.getDepartmentId(),
                        request.getBranchId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found in the selected branch"));

        Designation designation = designationRepository
                .findByIdAndDeletedFalse(request.getDesignationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Designation not found"));

        Employee manager = null;

        if (request.getReportingManagerId() != null) {
            manager = employeeRepository
                    .findScopedEmployee(
                            request.getReportingManagerId(),
                            companyId
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Reporting Manager not found in the selected company"));
        }

        Employee employee = mapper.toEntity(
                request,
                company,
                branch,
                department,
                designation,
                manager
        );

        employee.setEmployeeCode(generateEmployeeCode(companyId));

        employee = employeeRepository.save(employee);

        return mapper.toResponse(employee);
    }
    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Long companyId;

        if (securityUtils.isAdmin()) {
            companyId = request.getCompanyId();
        } else {
            companyId = securityUtils.getCurrentCompanyId();

            if (!companyId.equals(request.getCompanyId())) {
                throw new ForbiddenException(
                        "You are not allowed to move an employee to another company."
                );
            }
        }

        Employee employee = securityUtils.isAdmin()
                ? employeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"))
                : employeeRepository.findScopedEmployee(id, companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        Company company = companyRepository.findByIdAndDeletedFalse(companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        Branch branch = branchRepository
                .findByIdAndCompanyIdAndDeletedFalse(
                        request.getBranchId(),
                        companyId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Branch not found in the selected company"));

        Department department = departmentRepository
                .findByIdAndBranchIdAndDeletedFalse(
                        request.getDepartmentId(),
                        request.getBranchId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found in the selected branch"));

        Designation designation = designationRepository
                .findByIdAndDeletedFalse(request.getDesignationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Designation not found"));

        Employee manager = null;

        if (request.getReportingManagerId() != null) {

            if (request.getReportingManagerId().equals(id)) {
                throw new ForbiddenException(
                        "An employee cannot report to themselves."
                );
            }

            manager = employeeRepository
                    .findScopedEmployee(
                            request.getReportingManagerId(),
                            companyId
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Reporting Manager not found in the selected company"));
        }

        mapper.updateEntity(
                employee,
                request,
                company,
                branch,
                department,
                designation,
                manager
        );

        employee = employeeRepository.save(employee);

        return mapper.toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee;

        if (securityUtils.isAdmin()) {
            employee = employeeRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Employee not found"));
        } else {
            Long companyId = securityUtils.getCurrentCompanyId();

            employee = employeeRepository
                    .findScopedEmployee(id, companyId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Employee not found"));
        }

        return mapper.toResponse(employee);
    }
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {

        if (securityUtils.isAdmin()) {
            return employeeRepository.findAll()
                    .stream()
                    .filter(employee -> !Boolean.TRUE.equals(employee.getDeleted()))
                    .map(mapper::toResponse)
                    .toList();
        }

        Long companyId = securityUtils.getCurrentCompanyId();

        return employeeRepository
                .findByCompanyIdAndDeletedFalseOrderByIdDesc(companyId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getEmployees(Pageable pageable) {

        if (securityUtils.isAdmin()) {
            return employeeRepository.findAll(pageable)
                    .map(mapper::toResponse);
        }

        Long companyId = securityUtils.getCurrentCompanyId();

        return employeeRepository
                .findByCompanyIdAndDeletedFalse(companyId, pageable)
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
    @Transactional(readOnly = true)
    public String generateEmployeeCode(Long companyId) {

        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required.");
        }

        if (!securityUtils.isAdmin()) {

            Long currentCompanyId = securityUtils.getCurrentCompanyId();

            if (!currentCompanyId.equals(companyId)) {
                throw new ForbiddenException(
                        "You are not allowed to generate an employee code for another company."
                );
            }
        }

        companyRepository.findByIdAndDeletedFalse(companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        long count = employeeRepository.countByCompanyIdAndDeletedFalse(companyId);

        String employeeCode;

        do {
            count++;

            employeeCode = String.format("EMP%05d", count);

        } while (employeeRepository
                .existsByCompanyIdAndEmployeeCodeAndDeletedFalse(
                        companyId,
                        employeeCode
                ));

        return employeeCode;
    }
}