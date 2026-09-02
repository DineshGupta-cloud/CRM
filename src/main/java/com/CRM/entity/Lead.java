package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String leadCode;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(length = 100)
    private String middleName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 200)
    private String companyName;

    @Column(length = 100)
    private String designation;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 100)
    private String alternateEmail;

    @Column(nullable = false, length = 20)
    private String mobile;

    @Column(length = 20)
    private String alternateMobile;

    @Column(length = 20)
    private String phone;

    @Column(length = 200)
    private String website;

    @Column(length = 250)
    private String addressLine1;

    @Column(length = 250)
    private String addressLine2;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String country;

    @Column(length = 20)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_source_id")
    private LeadSource source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_status_id")
    private LeadStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_priority_id")
    private LeadPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @Column(precision = 18, scale = 2)
    private BigDecimal expectedRevenue;

    @Column(precision = 5, scale = 2)
    private BigDecimal probability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_employee_id")
    private Employee assignedEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_employee_id")
    private Employee createdEmployee;

    private LocalDate nextFollowUpDate;

    @Column(length = 2000)
    private String remarks;

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

    @Column(nullable = false)
    @Builder.Default
    private Boolean converted = false;

    private LocalDateTime convertedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "converted_customer_id")
    private Customer convertedCustomer;

}