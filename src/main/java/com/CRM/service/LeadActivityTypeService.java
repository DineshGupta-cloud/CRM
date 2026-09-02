package com.CRM.service;

import com.CRM.dto.request.LeadActivityTypeRequest;
import com.CRM.dto.response.LeadActivityTypeResponse;

import java.util.List;

public interface LeadActivityTypeService {

    LeadActivityTypeResponse save(LeadActivityTypeRequest request);

    LeadActivityTypeResponse update(Long id,
                                    LeadActivityTypeRequest request);

    LeadActivityTypeResponse findById(Long id);

    List<LeadActivityTypeResponse> findAll();

    void delete(Long id);

    String getNextActivityCode();

}