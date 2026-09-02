package com.CRM.mapper;

import com.CRM.dto.request.IndustryRequest;
import com.CRM.dto.response.IndustryResponse;
import com.CRM.entity.Industry;
import org.springframework.stereotype.Component;

@Component
public class IndustryMapper {

    public Industry toEntity(IndustryRequest request) {

        if (request == null) {
            return null;
        }

        return Industry.builder()
                .industryName(request.getIndustryName())
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public IndustryResponse toResponse(Industry entity) {

        if (entity == null) {
            return null;
        }

        return IndustryResponse.builder()
                .id(entity.getId())
                .industryCode(entity.getIndustryCode())
                .industryName(entity.getIndustryName())
                .description(entity.getDescription())
                .displayOrder(entity.getDisplayOrder())
                .active(entity.getActive())
                .build();
    }

    public void updateEntity(Industry entity,
                             IndustryRequest request) {

        entity.setIndustryName(request.getIndustryName());
        entity.setDescription(request.getDescription());
        entity.setDisplayOrder(request.getDisplayOrder());
    }
}