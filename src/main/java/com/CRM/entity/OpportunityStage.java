package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "opportunity_stages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String stageCode;

    @Column(nullable = false, unique = true, length = 100)
    private String stageName;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer displayOrder;

    @Builder.Default
    private Boolean isClosed = false;

    @Builder.Default
    private Boolean isWon = false;

    @Builder.Default
    private Boolean isLost = false;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Boolean deleted = false;
}