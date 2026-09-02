package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "lead_follow_up")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadFollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String followUpCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_up_type_id", nullable = false)
    private FollowUpType followUpType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private FollowUpStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_employee_id", nullable = false)
    private Employee assignedEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_by")
    private Employee completedBy;

    @Column(nullable = false)
    private LocalDate followUpDate;

    @Column(nullable = false)
    private LocalTime followUpTime;

    private Integer reminderBeforeMinutes;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(length = 3000)
    private String remarks;

    @Column(length = 3000)
    private String outcome;

    private LocalDate nextFollowUpDate;

    private LocalTime nextFollowUpTime;

    private LocalDateTime completedDate;

    @Builder.Default
    private Boolean completed = false;

    @Builder.Default
    private Boolean notificationSent = false;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Boolean deleted = false;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Version
    private Long version;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}