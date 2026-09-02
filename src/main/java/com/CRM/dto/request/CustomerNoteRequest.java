package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerNoteRequest {

    @NotNull(message = "Customer is required.")
    private Long customerId;

    @NotBlank(message = "Note is required.")
    private String note;

    @Builder.Default
    private Boolean pinned = false;

    @Builder.Default
    private Boolean privateNote = false;
}