package com.CRM.mapper;

import com.CRM.dto.request.CompanyRequest;
import com.CRM.dto.response.CompanyResponse;
import com.CRM.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toEntity(CompanyRequest request) {

        return Company.builder()
                .companyCode(request.getCompanyCode())
                .companyName(request.getCompanyName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .website(request.getWebsite())
                .gstNumber(request.getGstNumber())
                .panNumber(request.getPanNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pinCode(request.getPinCode())
                .active(request.getActive() == null ? true : request.getActive())
                .build();
    }

    public CompanyResponse toResponse(Company company) {

        return CompanyResponse.builder()
                .id(company.getId())
                .companyCode(company.getCompanyCode())
                .companyName(company.getCompanyName())
                .email(company.getEmail())
                .phone(company.getPhone())
                .website(company.getWebsite())
                .gstNumber(company.getGstNumber())
                .panNumber(company.getPanNumber())
                .address(company.getAddress())
                .city(company.getCity())
                .state(company.getState())
                .country(company.getCountry())
                .pinCode(company.getPinCode())
                .active(company.getActive())
                .build();
    }
}