package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerActivityRequest {

    @NotNull(message = "Customer is required.")
    private Long customerId;

    @NotBlank(message = "Activity code is required.")
    private String activityCode;

    @NotBlank(message = "Title is required.")
    private String title;

    private String description;

    private String oldValue;

    private String newValue;

    private String entityType;

    private Long entityId;

    @Builder.Default
    private Boolean visible = true;

}