package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadSourceResponse {

    private Long id;

    private String sourceCode;

    private String sourceName;

    private String description;

    private Integer displayOrder;

    private Boolean active;
}