package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadPriorityRequest {

    @NotBlank(message = "Priority name is required")
    private String priorityName;

    private String description;

    private Integer displayOrder;
}