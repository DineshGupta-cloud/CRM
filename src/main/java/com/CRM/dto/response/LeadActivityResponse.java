package com.CRM.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadActivityResponse {

    private Long id;

    private String activityNumber;

    private String leadCode;

    private String leadName;

    private String activityType;

    private String performedBy;

    private String title;

    private String description;

    private String oldValue;

    private String newValue;

    private String referenceType;

    private Long referenceId;

    private Boolean systemGenerated;

    private LocalDateTime createdDate;

}