package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadSourceRequest {

    @NotBlank(message = "Source name is required")
    private String sourceName;

    private String description;

    private Integer displayOrder;
}