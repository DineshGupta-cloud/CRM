package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignationRequest {

    @NotBlank(message = "Designation Code is required")
    private String designationCode;

    @NotBlank(message = "Designation Name is required")
    private String designationName;

    private Boolean active;
}