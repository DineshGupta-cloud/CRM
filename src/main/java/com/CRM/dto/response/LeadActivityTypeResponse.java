package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadActivityTypeResponse {

    private Long id;

    private String activityCode;

    private String activityName;

    private String description;

    private Boolean active;

}