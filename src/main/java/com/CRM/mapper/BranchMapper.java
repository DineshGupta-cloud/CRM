package com.CRM.mapper;

import com.CRM.dto.request.BranchRequest;
import com.CRM.dto.response.BranchResponse;
import com.CRM.entity.Branch;
import com.CRM.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public Branch toEntity(BranchRequest request, Company company) {

        return Branch.builder()
                .branchCode(request.getBranchCode())
                .branchName(request.getBranchName())
                .company(company)
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pinCode(request.getPinCode())
                .phone(request.getPhone())
                .email(request.getEmail())
                .active(request.getActive() == null ? true : request.getActive())
                .build();
    }

    public BranchResponse toResponse(Branch branch) {

        return BranchResponse.builder()
                .id(branch.getId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .companyId(branch.getCompany().getId())
                .companyName(branch.getCompany().getCompanyName())
                .address(branch.getAddress())
                .city(branch.getCity())
                .state(branch.getState())
                .country(branch.getCountry())
                .pinCode(branch.getPinCode())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .active(branch.getActive())
                .build();
    }
}