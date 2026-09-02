package com.CRM.service;

import com.CRM.dto.request.LeadPriorityRequest;
import com.CRM.dto.response.LeadPriorityResponse;

import java.util.List;

public interface LeadPriorityService {

    LeadPriorityResponse save(LeadPriorityRequest request);

    LeadPriorityResponse update(Long id, LeadPriorityRequest request);

    LeadPriorityResponse findById(Long id);

    List<LeadPriorityResponse> findAll();

    void delete(Long id);

    String getNextPriorityCode();
}