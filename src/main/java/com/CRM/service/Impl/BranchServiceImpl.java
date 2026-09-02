package com.CRM.service.Impl;

import com.CRM.dto.request.BranchRequest;
import com.CRM.dto.response.BranchResponse;
import com.CRM.entity.Branch;
import com.CRM.entity.Company;
import com.CRM.exception.DuplicateResourceException;
//import com.CRM.exception.ForbiddenException;
import com.CRM.exception.ForbiddenException;
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

        Company company;

        if (securityUtils.isAdmin()) {

            company = companyRepository.findByIdAndDeletedFalse(
                    request.getCompanyId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Company not found."));

        } else {

            Long currentCompanyId = securityUtils.getCurrentCompanyId();

            if (!currentCompanyId.equals(request.getCompanyId())) {
                throw new ForbiddenException(
                        "You are not authorized to create a branch for this company."
                );
            }

            company = companyRepository.findByIdAndDeletedFalse(
                    currentCompanyId
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Company not found."));
        }

        if (branchRepository.existsByCompanyIdAndBranchCodeAndDeletedFalse(
                company.getId(),
                request.getBranchCode())) {

            throw new DuplicateResourceException(
                    "Branch code already exists in this company."
            );
        }

        if (branchRepository.existsByCompanyIdAndBranchNameAndDeletedFalse(
                company.getId(),
                request.getBranchName())) {

            throw new DuplicateResourceException(
                    "Branch name already exists in this company."
            );
        }

        Branch branch = branchMapper.toEntity(request, company);

        branch = branchRepository.save(branch);

        return branchMapper.toResponse(branch);
    }

    @Override
    public BranchResponse update(Long id, BranchRequest request) {

        Branch branch;

        if (securityUtils.isAdmin()) {

            branch = branchRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Branch not found."));

        } else {

            Long currentCompanyId = securityUtils.getCurrentCompanyId();

            branch = branchRepository
                    .findByIdAndCompanyIdAndDeletedFalse(
                            id,
                            currentCompanyId
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Branch not found."));
        }

        Company company;

        if (securityUtils.isAdmin()) {

            company = companyRepository.findByIdAndDeletedFalse(
                    request.getCompanyId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Company not found."));

        } else {

            Long currentCompanyId = securityUtils.getCurrentCompanyId();

            if (!currentCompanyId.equals(request.getCompanyId())) {
                throw new ForbiddenException(
                        "You are not authorized to move this branch to another company."
                );
            }

            company = companyRepository.findByIdAndDeletedFalse(
                    currentCompanyId
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Company not found."));
        }

        if (!branch.getBranchCode().equals(request.getBranchCode())
                && branchRepository
                .existsByCompanyIdAndBranchCodeAndDeletedFalse(
                        company.getId(),
                        request.getBranchCode()
                )) {

            throw new DuplicateResourceException(
                    "Branch code already exists in this company."
            );
        }

        if (!branch.getBranchName().equals(request.getBranchName())
                && branchRepository
                .existsByCompanyIdAndBranchNameAndDeletedFalse(
                        company.getId(),
                        request.getBranchName()
                )) {

            throw new DuplicateResourceException(
                    "Branch name already exists in this company."
            );
        }

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
        branch.setActive(
                request.getActive() == null
                        ? branch.getActive()
                        : request.getActive()
        );

        branch = branchRepository.save(branch);

        return branchMapper.toResponse(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchResponse getById(Long id) {

        Branch branch;

        if (securityUtils.isAdmin()) {

            branch = branchRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Branch not found."));

        } else {

            Long companyId = securityUtils.getCurrentCompanyId();

            branch = branchRepository
                    .findByIdAndCompanyIdAndDeletedFalse(
                            id,
                            companyId
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Branch not found."));
        }

        return branchMapper.toResponse(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponse> getAll() {

        if (securityUtils.isAdmin()) {

            return branchRepository.findAll()
                    .stream()
                    .filter(branch -> !Boolean.TRUE.equals(branch.getDeleted()))
                    .map(branchMapper::toResponse)
                    .toList();
        }

        Long companyId = securityUtils.getCurrentCompanyId();

        return branchRepository
                .findByCompanyIdAndDeletedFalseOrderByIdDesc(companyId)
                .stream()
                .map(branchMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Branch branch;

        if (securityUtils.isAdmin()) {

            branch = branchRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Branch not found."));

        } else {

            Long companyId = securityUtils.getCurrentCompanyId();

            branch = branchRepository
                    .findByIdAndCompanyIdAndDeletedFalse(
                            id,
                            companyId
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Branch not found."));
        }

        branch.setActive(false);
        branch.setDeleted(true);
        branch.setDeletedBy(securityUtils.getCurrentUserId());
        branch.setDeletedDate(LocalDateTime.now());

        branchRepository.save(branch);
    }
}