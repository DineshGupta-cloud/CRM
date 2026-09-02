package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "opportunity_activity_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String typeCode;

    @Column(nullable = false)
    private String typeName;

    @Builder.Default
    private Boolean active = true;
}