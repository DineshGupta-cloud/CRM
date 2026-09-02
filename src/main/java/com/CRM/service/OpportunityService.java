package com.CRM.service;

import com.CRM.dto.request.OpportunityRequest;
import com.CRM.dto.response.OpportunityResponse;

import java.util.List;

public interface OpportunityService {

    OpportunityResponse create(OpportunityRequest request);

    OpportunityResponse update(Long id, OpportunityRequest request);

    OpportunityResponse getById(Long id);

    List<OpportunityResponse> getAll();

    List<OpportunityResponse> getByCustomer(Long customerId);

    void delete(Long id);
    void logActivity(
            Long opportunityId,
            String activityCode,
            Long employeeId,
            String title,
            String description,
            String oldValue,
            String newValue,
            String module,
            Long referenceId,
            Boolean success
    );

}