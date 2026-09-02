package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

    private Long id;

    private String roleCode;

    private String roleName;

    private String description;

    private Boolean active;
}