package com.CRM.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadFollowUpResponse {

    private Long id;

    private String followUpCode;

    private String leadCode;

    private String leadName;

    private String followUpType;

    private String status;

    private String assignedEmployee;

    private LocalDate followUpDate;

    private LocalTime followUpTime;

    private Integer reminderBeforeMinutes;

    private String subject;

    private String remarks;

    private String outcome;

    private LocalDate nextFollowUpDate;

    private LocalTime nextFollowUpTime;

    private Boolean completed;

    private LocalDateTime completedDate;

    private Boolean notificationSent;

    private Boolean active;
}