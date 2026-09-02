package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follow_up_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUpType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String typeCode;

    @Column(nullable = false, unique = true)
    private String typeName;

    private String description;

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