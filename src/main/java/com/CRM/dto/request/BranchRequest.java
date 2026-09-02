package com.CRM.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequest {

    @NotBlank(message = "Branch Code is required")
    @Size(max = 20, message = "Branch Code must not exceed 20 characters")
    private String branchCode;

    @NotBlank(message = "Branch Name is required")
    @Size(max = 100, message = "Branch Name must not exceed 100 characters")
    private String branchName;

    @NotNull(message = "Company is required")
    private Long companyId;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Size(max = 80, message = "City must not exceed 80 characters")
    private String city;

    @Size(max = 80, message = "State must not exceed 80 characters")
    private String state;

    @Size(max = 80, message = "Country must not exceed 80 characters")
    private String country;

    @Size(max = 10, message = "PIN Code must not exceed 10 characters")
    private String pinCode;

    @Size(max = 15, message = "Phone must not exceed 15 characters")
    private String phone;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    private Boolean active;
}