package com.CRM.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= BASIC =================

    @Column(nullable = false, length = 20)
    private String employeeCode;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String middleName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 10)
    private String mobileNumber;

    @Column(length = 10)
    private String gender;

    private LocalDate dateOfBirth;

    private LocalDate joiningDate;

    private LocalDate confirmationDate;

    private LocalDate relievingDate;

    @Column(length = 30)
    private String employmentType;

    @Column(length = 30)
    private String maritalStatus;

    @Column(length = 30)
    private String bloodGroup;

    // ================= ORGANIZATION =================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_id")
    private Designation designation;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee reportingManager;

    // ================= SALARY =================

    private BigDecimal basicSalary;

    private BigDecimal hra;

    private BigDecimal allowances;

    private BigDecimal bonus;

    private BigDecimal totalSalary;

    // ================= ADDRESS =================

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    // ================= DOCUMENTS =================

    @Column(length = 20)
    private String panNumber;

    @Column(length = 20)
    private String aadhaarNumber;

    @Column(length = 20)
    private String passportNumber;

    @Column(length = 20)
    private String drivingLicenseNumber;

    // ================= BANK =================

    private String bankName;

    private String accountNumber;

    private String ifscCode;

    private String accountHolderName;

    // ================= EMERGENCY =================

    private String emergencyContactName;

    private String emergencyContactNumber;

    private String emergencyRelationship;

    // ================= STATUS =================

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Boolean deleted = false;

    // ================= AUDIT =================

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