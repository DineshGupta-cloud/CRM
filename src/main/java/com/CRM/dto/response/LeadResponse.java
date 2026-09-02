package com.CRM.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadResponse {

    private Long id;

    private String leadCode;

    private String fullName;

    private String companyName;

    private String designation;

    private String email;

    private String mobile;

    private String city;

    private String state;

    private String source;

    private String status;

    private String priority;

    private String industry;

    private BigDecimal expectedRevenue;

    private BigDecimal probability;

    private String assignedEmployee;

    private LocalDate nextFollowUpDate;

    private Boolean active;
}