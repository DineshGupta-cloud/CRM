package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {

    private Long id;

    private String departmentCode;

    private String departmentName;

    private Long branchId;

    private String branchName;

    private Boolean active;
}