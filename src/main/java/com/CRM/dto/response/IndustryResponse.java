package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryResponse {

    private Long id;

    private String industryCode;

    private String industryName;

    private String description;

    private Integer displayOrder;

    private Boolean active;
}