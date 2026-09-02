package com.CRM.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest {

    @NotBlank(message = "Company Code is required")
    private String companyCode;

    @NotBlank(message = "Company Name is required")
    private String companyName;

    @Email
    private String email;

    private String phone;

    private String website;

    private String gstNumber;

    private String panNumber;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private Boolean active;
}