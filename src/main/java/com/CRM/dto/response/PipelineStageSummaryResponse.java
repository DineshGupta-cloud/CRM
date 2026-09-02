package com.CRM.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PipelineStageSummaryResponse {

    private Long stageId;
    private String stageName;

    private Long totalOpportunities;
    private BigDecimal totalRevenue;
    private BigDecimal weightedRevenue;
}