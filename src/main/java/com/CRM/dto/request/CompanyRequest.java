package com.CRM.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest {

    @NotBlank(message = "Company Code is required")
    @Size(max = 20, message = "Company Code must not exceed 20 characters")
    private String companyCode;

    @NotBlank(message = "Company Name is required")
    @Size(max = 100, message = "Company Name must not exceed 100 characters")
    private String companyName;

    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @Size(max = 30, message = "Phone must not exceed 30 characters")
    private String phone;

    @Size(max = 255, message = "Website must not exceed 255 characters")
    private String website;

    @Size(max = 30, message = "GST Number must not exceed 30 characters")
    private String gstNumber;

    @Size(max = 30, message = "PAN Number must not exceed 30 characters")
    private String panNumber;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @Size(max = 20, message = "PIN Code must not exceed 20 characters")
    private String pinCode;

    private Boolean active;
}