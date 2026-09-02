package com.CRM.mapper;

import com.CRM.dto.request.LeadPriorityRequest;
import com.CRM.dto.response.LeadPriorityResponse;
import com.CRM.entity.LeadPriority;
import org.springframework.stereotype.Component;

@Component
public class LeadPriorityMapper {

    public LeadPriority toEntity(LeadPriorityRequest request) {

        if (request == null) {
            return null;
        }

        return LeadPriority.builder()
                .priorityName(request.getPriorityName())
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public LeadPriorityResponse toResponse(LeadPriority entity) {

        if (entity == null) {
            return null;
        }

        return LeadPriorityResponse.builder()
                .id(entity.getId())
                .priorityCode(entity.getPriorityCode())
                .priorityName(entity.getPriorityName())
                .description(entity.getDescription())
                .displayOrder(entity.getDisplayOrder())
                .active(entity.getActive())
                .build();
    }

    public void updateEntity(LeadPriority entity,
                             LeadPriorityRequest request) {

        entity.setPriorityName(request.getPriorityName());
        entity.setDescription(request.getDescription());
        entity.setDisplayOrder(request.getDisplayOrder());
    }
}