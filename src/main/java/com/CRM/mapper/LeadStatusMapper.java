package com.CRM.mapper;

import com.CRM.dto.request.LeadStatusRequest;
import com.CRM.dto.response.LeadStatusResponse;
import com.CRM.entity.LeadStatus;
import org.springframework.stereotype.Component;

@Component
public class LeadStatusMapper {

    public LeadStatus toEntity(LeadStatusRequest request) {

        if (request == null) {
            return null;
        }

        return LeadStatus.builder()
                .statusName(request.getStatusName())
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public LeadStatusResponse toResponse(LeadStatus entity) {

        if (entity == null) {
            return null;
        }

        return LeadStatusResponse.builder()
                .id(entity.getId())
                .statusCode(entity.getStatusCode())
                .statusName(entity.getStatusName())
                .description(entity.getDescription())
                .displayOrder(entity.getDisplayOrder())
                .active(entity.getActive())
                .build();
    }

    public void updateEntity(
            LeadStatus entity,
            LeadStatusRequest request) {

        entity.setStatusName(request.getStatusName());
        entity.setDescription(request.getDescription());
        entity.setDisplayOrder(request.getDisplayOrder());
    }
}