package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadActivityTypeRequest {

    @NotBlank(message = "Activity name is required")
    private String activityName;

    private String description;

}