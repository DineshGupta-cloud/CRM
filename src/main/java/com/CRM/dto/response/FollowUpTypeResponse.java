package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUpTypeResponse {

    private Long id;

    private String typeCode;

    private String typeName;

    private String description;

    private Boolean active;

}