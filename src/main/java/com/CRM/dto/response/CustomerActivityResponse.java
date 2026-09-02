package com.CRM.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerActivityResponse {

    private Long id;

    private String customerCode;

    private String customerName;

    private String activityCode;

    private String activityName;

    private String performedBy;

    private String title;

    private String description;

    private String oldValue;

    private String newValue;

    private String entityType;

    private Long entityId;

    private Boolean visible;

    private LocalDateTime activityDate;

}