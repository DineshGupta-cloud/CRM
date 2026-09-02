package com.CRM.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityActivityRequest {

    @NotNull
    private Long opportunityId;

    private String activityCode;

    private Long activityTypeId;

    private String title;

    private String description;

    private String oldValue;

    private String newValue;
}