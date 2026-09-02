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
public class LeadRequest {

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String companyName;

    private String designation;

    @Email
    private String email;

    private String alternateEmail;

    @NotBlank
    private String mobile;

    private String alternateMobile;

    private String phone;

    private String website;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    @NotNull
    private Long sourceId;

    @NotNull
    private Long statusId;

    @NotNull
    private Long priorityId;

    @NotNull
    private Long industryId;

    @NotNull
    private Long assignedEmployeeId;

    private BigDecimal expectedRevenue;

    private BigDecimal probability;

    private LocalDate nextFollowUpDate;

    private String remarks;
}