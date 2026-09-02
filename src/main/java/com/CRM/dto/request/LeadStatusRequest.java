package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadStatusRequest {

    @NotBlank(message = "Status name is required")
    private String statusName;

    private String description;

    private Integer displayOrder;
}