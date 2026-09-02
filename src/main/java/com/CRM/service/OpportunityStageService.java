package com.CRM.service;

import com.CRM.dto.request.OpportunityStageRequest;
import com.CRM.dto.response.OpportunityStageResponse;

import java.util.List;

public interface OpportunityStageService {

    OpportunityStageResponse create(OpportunityStageRequest request);

    OpportunityStageResponse update(Long id, OpportunityStageRequest request);

    OpportunityStageResponse getById(Long id);

    List<OpportunityStageResponse> getAll();

    void delete(Long id);
}