package com.CRM.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadNoteResponse {

    private Long id;

    private String noteCode;

    private String leadCode;

    private String employeeName;

    private String note;

    private Boolean pinned;

    private Boolean privateNote;

    private LocalDateTime createdDate;
}