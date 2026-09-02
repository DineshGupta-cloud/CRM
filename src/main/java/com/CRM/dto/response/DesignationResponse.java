package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignationResponse {

    private Long id;

    private String designationCode;

    private String designationName;

    private Boolean active;
}