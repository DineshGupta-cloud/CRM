package com.CRM.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    EmployeeRequest {

    @NotBlank(message = "First Name is required")
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @NotBlank(message = "Last Name is required")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    private String gender;

    private LocalDate dateOfBirth;

    @NotNull(message = "Joining Date is required")
    private LocalDate joiningDate;

    private LocalDate confirmationDate;

    private LocalDate relievingDate;

    private String employmentType;

    private String maritalStatus;

    private String bloodGroup;

    @NotNull(message = "Company is required")
    private Long companyId;

    @NotNull(message = "Branch is required")
    private Long branchId;

    @NotNull(message = "Department is required")
    private Long departmentId;

    @NotNull(message = "Designation is required")
    private Long designationId;

    @NotNull(message = "Role is required")
    private Long roleId;

    private Long reportingManagerId;

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

    private String panNumber;

    private String aadhaarNumber;

    private String passportNumber;

    private String drivingLicenseNumber;

    private String bankName;

    private String accountNumber;

    private String ifscCode;

    private String accountHolderName;

    private String emergencyContactName;

    private String emergencyContactNumber;

    private String emergencyRelationship;

    private Boolean active;
}