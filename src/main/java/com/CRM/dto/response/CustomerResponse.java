package com.CRM.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    private String customerCode;

    private String customerName;

    private String companyName;

    private String designation;

    private String email;

    private String mobile;

    private String city;

    private String state;

    private String country;

    private String gstNumber;

    private String panNumber;

    private String industry;

    private String assignedEmployee;

    private String leadCode;

    private LocalDate customerSince;

    private BigDecimal creditLimit;

    private BigDecimal annualRevenue;

    private Boolean active;

    private LocalDateTime createdDate;

}