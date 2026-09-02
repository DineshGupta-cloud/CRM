package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadStatusResponse {

    private Long id;

    private String statusCode;

    private String statusName;

    private String description;

    private Integer displayOrder;

    private Boolean active;
}