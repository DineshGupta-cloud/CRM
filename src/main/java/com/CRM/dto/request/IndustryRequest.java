package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryRequest {

    @NotBlank(message = "Industry name is required")
    private String industryName;

    private String description;

    private Integer displayOrder;
}