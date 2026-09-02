package com.CRM.dto.request;

import lombok.*;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadFollowUpRequest {

    @NotNull(message = "Lead is required")
    private Long leadId;

    @NotNull(message = "Follow-up type is required")
    private Long followUpTypeId;

    @NotNull(message = "Status is required")
    private Long statusId;

    @NotNull(message = "Assigned employee is required")
    private Long assignedEmployeeId;

    @NotNull(message = "Follow-up date is required")
    @FutureOrPresent(message = "Follow-up date cannot be in the past")
    private LocalDate followUpDate;

    @NotNull(message = "Follow-up time is required")
    private LocalTime followUpTime;

    private Integer reminderBeforeMinutes;

    @NotBlank(message = "Subject is required")
    private String subject;

    private String remarks;

    private String outcome;

    private LocalDate nextFollowUpDate;

    private LocalTime nextFollowUpTime;
}