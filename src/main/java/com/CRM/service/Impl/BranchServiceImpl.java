package com.CRM.service.Impl;

import com.CRM.dto.request.BranchRequest;
import com.CRM.dto.response.BranchResponse;
import com.CRM.entity.Branch;
import com.CRM.entity.Company;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.BranchMapper;
import com.CRM.repository.BranchRepository;
import com.CRM.repository.CompanyRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;
    private final BranchMapper branchMapper;
    private final SecurityUtils securityUtils;

    @Override
    public BranchResponse create(BranchRequest request) {

        if (branchRepository.existsByBranchCode(request.getBranchCode())) {
            throw new DuplicateResourceException("Branch code already exists.");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        Branch branch = branchMapper.toEntity(request, company);

        branch = branchRepository.save(branch);

        return branchMapper.toResponse(branch);
    }

    @Override
    public BranchResponse update(Long id, BranchRequest request) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found."));

        if (!branch.getBranchCode().equals(request.getBranchCode())
                && branchRepository.existsByBranchCode(request.getBranchCode())) {

            throw new DuplicateResourceException("Branch code already exists.");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        branch.setBranchCode(request.getBranchCode());
        branch.setBranchName(request.getBranchName());
        branch.setCompany(company);
        branch.setAddress(request.getAddress());
        branch.setCity(request.getCity());
        branch.setState(request.getState());
        branch.setCountry(request.getCountry());
        branch.setPinCode(request.getPinCode());
        branch.setPhone(request.getPhone());
        branch.setEmail(request.getEmail());
        branch.setActive(request.getActive());

        branch = branchRepository.save(branch);

        return branchMapper.toResponse(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchResponse getById(Long id) {

        Branch branch = branchRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found."));

        return branchMapper.toResponse(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponse> getAll() {

        return branchRepository.findByDeletedFalse()
                .stream()
                .map(branchMapper::toResponse)
                .toList();

    }

    @Override
    public void delete(Long id) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        branch.setActive(false);
        branch.setDeleted(true);
//        branch.setDeletedBy(securityUtils.getCurrentEmployeeId());
        branch.setDeletedBy(securityUtils.getCurrentUserId());
        branch.setDeletedDate(LocalDateTime.now());

        branchRepository.save(branch);
    }

}