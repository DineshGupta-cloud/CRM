package com.CRM.service;

import com.CRM.dto.request.OpportunityProductRequest;
import com.CRM.dto.response.OpportunityProductResponse;

import java.util.List;

public interface OpportunityProductService {

    OpportunityProductResponse add(OpportunityProductRequest request);

    OpportunityProductResponse update(Long id, OpportunityProductRequest request);

    List<OpportunityProductResponse> getByOpportunity(Long opportunityId);

    void delete(Long id);
}