package com.CRM.service.Impl;

import com.CRM.dto.request.CompanyRequest;
import com.CRM.dto.response.CompanyResponse;
import com.CRM.entity.Company;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.CompanyMapper;
import com.CRM.repository.CompanyRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final SecurityUtils securityUtils;

    @Override
    public CompanyResponse create(CompanyRequest request) {

        if (companyRepository.existsByCompanyCodeAndDeletedFalse(
                request.getCompanyCode())) {

            throw new DuplicateResourceException(
                    "Company Code already exists."
            );
        }

        Company company = companyMapper.toEntity(request);

        company = companyRepository.save(company);

        return companyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse update(Long id, CompanyRequest request) {

        Company company = companyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        if (!company.getCompanyCode().equals(request.getCompanyCode())
                && companyRepository.existsByCompanyCodeAndDeletedFalse(
                request.getCompanyCode())) {

            throw new DuplicateResourceException(
                    "Company Code already exists."
            );
        }

        company.setCompanyCode(request.getCompanyCode());
        company.setCompanyName(request.getCompanyName());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setWebsite(request.getWebsite());
        company.setGstNumber(request.getGstNumber());
        company.setPanNumber(request.getPanNumber());
        company.setAddress(request.getAddress());
        company.setCity(request.getCity());
        company.setState(request.getState());
        company.setCountry(request.getCountry());
        company.setPinCode(request.getPinCode());
        company.setActive(request.getActive());

        company = companyRepository.save(company);

        return companyMapper.toResponse(company);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getById(Long id) {

        Company company = companyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        return companyMapper.toResponse(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getAll() {

        return companyRepository.findByDeletedFalse()
                .stream()
                .map(companyMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Company company = companyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        company.setActive(false);
        company.setDeleted(true);
        company.setDeletedBy(securityUtils.getCurrentUserId());
        company.setDeletedDate(LocalDateTime.now());

        companyRepository.save(company);
    }
}