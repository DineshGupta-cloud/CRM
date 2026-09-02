package com.CRM.service;

import com.CRM.dto.request.LeadRequest;
import com.CRM.dto.response.LeadResponse;

import java.util.List;

public interface LeadService {

    LeadResponse save(LeadRequest request);

    LeadResponse update(Long id,
                        LeadRequest request);

    LeadResponse findById(Long id);

    List<LeadResponse> findAll();

    void delete(Long id);

    String getNextLeadCode();

}