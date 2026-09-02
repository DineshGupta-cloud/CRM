package com.CRM.mapper;

import com.CRM.dto.request.FollowUpTypeRequest;
import com.CRM.dto.response.FollowUpTypeResponse;
import com.CRM.entity.FollowUpType;
import org.springframework.stereotype.Component;

@Component
public class FollowUpTypeMapper {

    public FollowUpType toEntity(FollowUpTypeRequest request) {

        if (request == null) {
            return null;
        }

        return FollowUpType.builder()
                .typeName(request.getTypeName())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(FollowUpType entity,
                             FollowUpTypeRequest request) {

        entity.setTypeName(request.getTypeName());
        entity.setDescription(request.getDescription());
    }

    public FollowUpTypeResponse toResponse(FollowUpType entity) {

        if (entity == null) {
            return null;
        }

        return FollowUpTypeResponse.builder()
                .id(entity.getId())
                .typeCode(entity.getTypeCode())
                .typeName(entity.getTypeName())
                .description(entity.getDescription())
                .active(entity.getActive())
                .build();
    }

}