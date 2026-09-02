package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityStageResponse {

    private Long id;

    private String stageCode;

    private String stageName;

    private String description;

    private Integer displayOrder;

    private Boolean isClosed;

    private Boolean isWon;

    private Boolean isLost;

    private Boolean active;
}