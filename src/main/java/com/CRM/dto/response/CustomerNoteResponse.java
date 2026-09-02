package com.CRM.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerNoteResponse {

    private Long id;

    private String noteCode;

    private String customerCode;

    private String customerName;

    private String employeeName;

    private String note;

    private Boolean pinned;

    private Boolean privateNote;

    private LocalDateTime createdDate;
}