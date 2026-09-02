package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "branches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String branchCode;

    @Column(nullable = false, length = 100)
    private String branchName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(length = 255)
    private String address;

    @Column(length = 80)
    private String city;

    @Column(length = 80)
    private String state;

    @Column(length = 80)
    private String country;

    @Column(length = 10)
    private String pinCode;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String email;

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