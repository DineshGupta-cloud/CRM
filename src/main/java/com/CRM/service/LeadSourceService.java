package com.CRM.service;

import com.CRM.dto.request.LeadSourceRequest;
import com.CRM.dto.response.LeadSourceResponse;

import java.util.List;

public interface LeadSourceService {

    LeadSourceResponse save(LeadSourceRequest request);

    LeadSourceResponse update(Long id, LeadSourceRequest request);

    LeadSourceResponse findById(Long id);

    List<LeadSourceResponse> findAll();

    void delete(Long id);

    String getNextSourceCode();
}