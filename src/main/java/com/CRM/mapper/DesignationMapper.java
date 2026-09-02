package com.CRM.mapper;

import com.CRM.dto.request.DesignationRequest;
import com.CRM.dto.response.DesignationResponse;
import com.CRM.entity.Designation;
import org.springframework.stereotype.Component;

@Component
public class DesignationMapper {

    public Designation toEntity(DesignationRequest request) {

        return Designation.builder()
                .designationCode(request.getDesignationCode())
                .designationName(request.getDesignationName())
                .active(request.getActive() == null ? true : request.getActive())
                .build();
    }

    public DesignationResponse toResponse(Designation designation) {

        return DesignationResponse.builder()
                .id(designation.getId())
                .designationCode(designation.getDesignationCode())
                .designationName(designation.getDesignationName())
                .active(designation.getActive())
                .build();
    }
}