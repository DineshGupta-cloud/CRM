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
public class OpportunityRequest {

    @NotBlank(message = "Opportunity name is required.")
    private String opportunityName;

    @NotNull(message = "Customer is required.")
    private Long customerId;

    @NotNull(message = "Stage is required.")
    private Long stageId;

    private Long assignedEmployeeId;

    @DecimalMin(value = "0.00")
    private BigDecimal expectedRevenue;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    private BigDecimal probability;

    private LocalDate expectedCloseDate;

    private String description;
}