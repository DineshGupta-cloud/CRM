package com.CRM.service;

import com.CRM.entity.OpportunityActivity;

import java.util.List;

public interface OpportunityActivityService {

    void logActivity(Long opportunityId,
                     String activityCode,
                     Long employeeId,
                     String title,
                     String description,
                     String oldValue,
                     String newValue,
                     String module,
                     Long referenceId,
                     Boolean systemGenerated);

    List<OpportunityActivity> getByOpportunityId(Long opportunityId);
}