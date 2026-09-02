package com.CRM.service.Impl;

import com.CRM.dto.request.DepartmentRequest;
import com.CRM.dto.response.DepartmentResponse;
import com.CRM.entity.Branch;
import com.CRM.entity.Company;
import com.CRM.entity.Department;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.DepartmentMapper;
import com.CRM.repository.BranchRepository;
import com.CRM.repository.DepartmentRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final BranchRepository branchRepository;
    private final DepartmentMapper departmentMapper;

    private final SecurityUtils securityUtils;

    @Override
    public DepartmentResponse create(DepartmentRequest request) {

        if (departmentRepository.existsByDepartmentCode(request.getDepartmentCode())) {
            throw new DuplicateResourceException("Department code already exists.");
        }

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found."));

        Department department = departmentMapper.toEntity(request, branch);

        department = departmentRepository.save(department);

        return departmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        if (!department.getDepartmentCode().equals(request.getDepartmentCode())
                && departmentRepository.existsByDepartmentCode(request.getDepartmentCode())) {

            throw new DuplicateResourceException("Department code already exists.");
        }

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found."));

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setBranch(branch);
        department.setActive(request.getActive());

        department = departmentRepository.save(department);

        return departmentMapper.toResponse(department);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(Long id) {

        Department department = departmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        return departmentMapper.toResponse(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAll() {

        return departmentRepository.findByDeletedFalse()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {


        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        department.setActive(false);
        department.setDeleted(true);
        department.setDeletedBy(securityUtils.getCurrentUserId());
        department.setDeletedDate(LocalDateTime.now());

        departmentRepository.save(department);

    }
}