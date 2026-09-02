package com.CRM.service;

import com.CRM.dto.request.CompanyRequest;
import com.CRM.dto.response.CompanyResponse;

import java.util.List;

public interface CompanyService {


    CompanyResponse create(CompanyRequest request);

    CompanyResponse update(Long id, CompanyRequest request);

    CompanyResponse getById(Long id);

    List<CompanyResponse> getAll();

    void delete(Long id);


}