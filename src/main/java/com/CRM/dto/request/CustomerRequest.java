package com.CRM.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank(message = "Customer name is required.")
    @Size(max = 150)
    private String customerName;

    @NotBlank(message = "Company name is required.")
    @Size(max = 200)
    private String companyName;

    @Size(max = 100)
    private String designation;

    @Email(message = "Invalid email format.")
    @Size(max = 120)
    private String email;

    @Email(message = "Invalid alternate email format.")
    @Size(max = 120)
    private String alternateEmail;

    @Size(max = 20)
    private String mobile;

    @Size(max = 20)
    private String alternateMobile;

    @Size(max = 20)
    private String phone;

    @Size(max = 150)
    private String website;

    @Size(max = 20)
    private String gstNumber;

    @Size(max = 20)
    private String panNumber;

    @Size(max = 255)
    private String addressLine1;

    @Size(max = 255)
    private String addressLine2;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String state;

    @Size(max = 100)
    private String country;

    @Size(max = 10)
    private String postalCode;

    private LocalDate customerSince;

    @DecimalMin(value = "0.0", message = "Credit limit must be positive.")
    private BigDecimal creditLimit;

    @DecimalMin(value = "0.0", message = "Annual revenue must be positive.")
    private BigDecimal annualRevenue;

    @Size(max = 1000)
    private String remarks;

    private Long industryId;

    private Long assignedEmployeeId;

    /**
     * Optional.
     * Used when converting a Lead to Customer.
     */
    private Long leadId;

    @Builder.Default
    private Boolean active = true;

}