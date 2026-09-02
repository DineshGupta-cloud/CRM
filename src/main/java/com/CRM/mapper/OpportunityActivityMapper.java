package com.CRM.mapper;

import com.CRM.dto.request.OpportunityActivityRequest;
import com.CRM.dto.response.OpportunityActivityResponse;
import com.CRM.entity.OpportunityActivity;
import com.CRM.entity.OpportunityActivityType;
import org.springframework.stereotype.Component;

@Component
public class OpportunityActivityMapper {

    public OpportunityActivity toEntity(OpportunityActivityRequest request,
                                        OpportunityActivityType type) {

        return OpportunityActivity.builder()
                .opportunityId(request.getOpportunityId())
                .activityCode(request.getActivityCode())
                .activityType(type)
                .title(request.getTitle())
                .description(request.getDescription())
                .oldValue(request.getOldValue())
                .newValue(request.getNewValue())
                .module("OPPORTUNITY")
                .referenceId(request.getOpportunityId())
                .success(true)
                .build();
    }

    public OpportunityActivityResponse toResponse(OpportunityActivity activity) {

        return OpportunityActivityResponse.builder()
                .id(activity.getId())
                .opportunityId(activity.getOpportunityId())
                .activityCode(activity.getActivityCode())
                .activityType(activity.getActivityType() != null
                        ? activity.getActivityType().getTypeName()
                        : null)
                .employeeId(activity.getEmployeeId())
                .title(activity.getTitle())
                .description(activity.getDescription())
                .oldValue(activity.getOldValue())
                .newValue(activity.getNewValue())
                .module(activity.getModule())
                .referenceId(activity.getReferenceId())
                .createdDate(activity.getCreatedDate())
                .build();
    }
}