package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "customers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "customer_code"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "mobile"),
                @UniqueConstraint(columnNames = "gst_number")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //====================================================
    // Basic Information
    //====================================================

    @Column(name = "customer_code", nullable = false, length = 20)
    private String customerCode;

    @Column(name = "customer_name", nullable = false, length = 150)
    private String customerName;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(length = 100)
    private String designation;

    //====================================================
    // Contact Information
    //====================================================

    @Column(length = 120)
    private String email;

    @Column(name = "alternate_email", length = 120)
    private String alternateEmail;

    @Column(length = 20)
    private String mobile;

    @Column(name = "alternate_mobile", length = 20)
    private String alternateMobile;

    @Column(length = 20)
    private String phone;

    @Column(length = 150)
    private String website;

    //====================================================
    // Tax Information
    //====================================================

    @Column(name = "gst_number", length = 20)
    private String gstNumber;

    @Column(name = "pan_number", length = 20)
    private String panNumber;

    //====================================================
    // Address
    //====================================================

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String country;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    //====================================================
    // Business Information
    //====================================================

    @Column(name = "customer_since")
    private LocalDate customerSince;

    @Column(name = "credit_limit", precision = 18, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "annual_revenue", precision = 18, scale = 2)
    private BigDecimal annualRevenue;

    @Column(length = 1000)
    private String remarks;

    //====================================================
    // Relations
    //====================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_employee_id")
    private Employee assignedEmployee;

    /**
     * Lead from which this customer was converted.
     * Will be null for manually created customers.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private Lead lead;

    //====================================================
    // Audit
    //====================================================

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}