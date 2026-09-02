package com.CRM.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityActivityResponse {

    private Long id;

    private Long opportunityId;

    private String activityCode;

    private String activityType;

    private Long employeeId;

    private String title;

    private String description;

    private String oldValue;

    private String newValue;

    private String module;

    private Long referenceId;

    private LocalDateTime createdDate;
}