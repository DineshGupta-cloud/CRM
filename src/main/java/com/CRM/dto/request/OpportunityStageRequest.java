package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityStageRequest {

    @NotBlank(message = "Stage code is required.")
    private String stageCode;

    @NotBlank(message = "Stage name is required.")
    private String stageName;

    private String description;

    @NotNull(message = "Display order is required.")
    private Integer displayOrder;

    @Builder.Default
    private Boolean isClosed = false;

    @Builder.Default
    private Boolean isWon = false;

    @Builder.Default
    private Boolean isLost = false;

    @Builder.Default
    private Boolean active = true;
}