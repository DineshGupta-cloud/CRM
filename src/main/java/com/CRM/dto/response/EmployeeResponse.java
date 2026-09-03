package com.CRM.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;

    private String employeeCode;

    private String firstName;

    private String middleName;

    private String lastName;

    private String fullName;

    private String email;

    private String mobileNumber;

    private String gender;

    private LocalDate dateOfBirth;

    private LocalDate joiningDate;

    private LocalDate confirmationDate;

    private LocalDate relievingDate;

    private String employmentType;

    private String maritalStatus;

    private String bloodGroup;

    private Long companyId;
    private String companyName;

    private Long branchId;
    private String branchName;

    private Long departmentId;
    private String departmentName;

    private Long designationId;
    private String designationName;

//    private Long roleId;
//    private String roleName;

    private Long reportingManagerId;
    private String reportingManagerName;

    private BigDecimal basicSalary;

    private BigDecimal hra;

    private BigDecimal allowances;

    private BigDecimal bonus;

    private BigDecimal totalSalary;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private String bankName;

    private String accountNumber;

    private String ifscCode;

    private Boolean active;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}