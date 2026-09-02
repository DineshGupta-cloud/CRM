package com.CRM.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadActivityRequest {

    private Long leadId;

    private Long activityTypeId;

    private Long performedById;

    private String title;

    private String description;

    private String oldValue;

    private String newValue;

    private String referenceType;

    private Long referenceId;

}