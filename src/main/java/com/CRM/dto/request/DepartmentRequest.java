package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRequest {

    @NotBlank(message = "Department Code is required")
    private String departmentCode;

    @NotBlank(message = "Department Name is required")
    private String departmentName;

    @NotNull(message = "Branch is required")
    private Long branchId;

    private Boolean active;
}