package com.CRM.mapper;

import com.CRM.dto.request.LeadActivityTypeRequest;
import com.CRM.dto.response.LeadActivityTypeResponse;
import com.CRM.entity.LeadActivityType;
import org.springframework.stereotype.Component;

@Component
public class LeadActivityTypeMapper {

    public LeadActivityType toEntity(LeadActivityTypeRequest request) {

        if (request == null) {
            return null;
        }

        return LeadActivityType.builder()
                .activityName(request.getActivityName())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(LeadActivityType entity,
                             LeadActivityTypeRequest request) {

        entity.setActivityName(request.getActivityName());
        entity.setDescription(request.getDescription());
    }

    public LeadActivityTypeResponse toResponse(LeadActivityType entity) {

        if (entity == null) {
            return null;
        }

        return LeadActivityTypeResponse.builder()
                .id(entity.getId())
                .activityCode(entity.getActivityCode())
                .activityName(entity.getActivityName())
                .description(entity.getDescription())
                .active(entity.getActive())
                .build();
    }
}