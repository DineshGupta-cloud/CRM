package com.CRM.mapper;

import com.CRM.dto.request.LeadSourceRequest;
import com.CRM.dto.response.LeadSourceResponse;
import com.CRM.entity.LeadSource;
import org.springframework.stereotype.Component;

@Component
public class LeadSourceMapper {

    public LeadSource toEntity(LeadSourceRequest request) {

        if (request == null) {
            return null;
        }

        return LeadSource.builder()
                .sourceName(request.getSourceName())
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public LeadSourceResponse toResponse(LeadSource entity) {

        if (entity == null) {
            return null;
        }

        return LeadSourceResponse.builder()
                .id(entity.getId())
                .sourceCode(entity.getSourceCode())
                .sourceName(entity.getSourceName())
                .description(entity.getDescription())
                .displayOrder(entity.getDisplayOrder())
                .active(entity.getActive())
                .build();
    }

    public void updateEntity(LeadSource entity, LeadSourceRequest request) {

        entity.setSourceName(request.getSourceName());
        entity.setDescription(request.getDescription());
        entity.setDisplayOrder(request.getDisplayOrder());
    }
}