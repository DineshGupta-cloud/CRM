package com.CRM.mapper;

import com.CRM.dto.request.RoleRequest;
import com.CRM.dto.response.RoleResponse;
import com.CRM.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toEntity(RoleRequest request) {

        return Role.builder()
                .roleCode(request.getRoleCode())
                .roleName(request.getRoleName())
                .description(request.getDescription())
                .active(request.getActive() == null ? true : request.getActive())
                .build();
    }

    public RoleResponse toResponse(Role role) {

        return RoleResponse.builder()
                .id(role.getId())
                .roleCode(role.getRoleCode())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .active(role.getActive())
                .build();
    }
}