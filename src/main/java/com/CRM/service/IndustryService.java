package com.CRM.service;

import com.CRM.dto.request.IndustryRequest;
import com.CRM.dto.response.IndustryResponse;

import java.util.List;

public interface IndustryService {

    IndustryResponse save(IndustryRequest request);

    IndustryResponse update(Long id,
                            IndustryRequest request);

    IndustryResponse findById(Long id);

    List<IndustryResponse> findAll();

    void delete(Long id);

    String getNextIndustryCode();

}