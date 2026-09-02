package com.CRM.service;

import com.CRM.dto.response.LeadActivityResponse;

import java.util.List;

public interface LeadActivityService {

    void logActivity(
            Long leadId,
            String activityCode,
            Long employeeId,
            String title,
            String description,
            String oldValue,
            String newValue,
            String referenceType,
            Long referenceId,
            boolean systemGenerated
    );

    List<LeadActivityResponse> findByLead(Long leadId);

    List<LeadActivityResponse> findAll();

    void delete(Long id);

    String getNextActivityNumber();
}