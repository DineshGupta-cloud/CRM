package com.CRM.service;

import com.CRM.dto.request.LeadStatusRequest;
import com.CRM.dto.response.LeadStatusResponse;

import java.util.List;

public interface LeadStatusService {

    LeadStatusResponse save(LeadStatusRequest request);

    LeadStatusResponse update(Long id, LeadStatusRequest request);

    LeadStatusResponse findById(Long id);

    List<LeadStatusResponse> findAll();

    void delete(Long id);

    String getNextStatusCode();
}