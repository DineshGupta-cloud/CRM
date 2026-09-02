package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "opportunity_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long opportunityId;

    private String activityCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_type_id")
    private OpportunityActivityType activityType;

    private Long employeeId;

    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String oldValue;

    @Column(length = 2000)
    private String newValue;

    private String module;

    private Long referenceId;

    @Builder.Default
    private Boolean success = true;

    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }
}