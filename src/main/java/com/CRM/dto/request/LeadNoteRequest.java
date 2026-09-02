package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadNoteRequest {

    @NotNull
    private Long leadId;

    @NotBlank
    private String note;

    private Boolean pinned;

    private Boolean privateNote;
}