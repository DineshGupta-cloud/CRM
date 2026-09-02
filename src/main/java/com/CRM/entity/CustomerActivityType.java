package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_activity_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String activityCode;

    @Column(nullable = false, unique = true, length = 100)
    private String activityName;

    @Column(length = 500)
    private String description;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Boolean deleted = false;
}