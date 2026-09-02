package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequest {

    @NotBlank(message = "Branch Code is required")
    private String branchCode;

    @NotBlank(message = "Branch Name is required")
    private String branchName;

    @NotNull(message = "Company is required")
    private Long companyId;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private String phone;

    private String email;

    private Boolean active;
}