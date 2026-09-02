package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "designations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String designationCode;

    @Column(nullable = false, length = 100)
    private String designationName;

    @Builder.Default
    private Boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

    @Column(name = "deleted",nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}