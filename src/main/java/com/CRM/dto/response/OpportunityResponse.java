package com.CRM.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityResponse {

    private Long id;

    private String opportunityCode;

    private String opportunityName;

    private String customerCode;

    private String customerName;

    private String stage;

    private String assignedEmployee;

    private BigDecimal expectedRevenue;

    private BigDecimal probability;

    private LocalDate expectedCloseDate;

    private String description;

    private Boolean active;
}