package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUpTypeRequest {

    @NotBlank(message = "Follow-up type name is required")
    private String typeName;

    private String description;

}