package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {

    @NotBlank(message = "Role Code is required")
    private String roleCode;

    @NotBlank(message = "Role Name is required")
    private String roleName;

    private String description;

    private Boolean active;
}