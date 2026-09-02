package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadPriorityResponse {

    private Long id;

    private String priorityCode;

    private String priorityName;

    private String description;

    private Integer displayOrder;

    private Boolean active;
}