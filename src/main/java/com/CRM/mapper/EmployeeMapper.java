package com.CRM.mapper;

import com.CRM.dto.request.EmployeeRequest;
import com.CRM.dto.response.EmployeeResponse;
import com.CRM.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest request,
                             Company company,
                             Branch branch,
                             Department department,
                             Designation designation,
                             Role role,
                             Employee manager) {

        return Employee.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .joiningDate(request.getJoiningDate())
                .confirmationDate(request.getConfirmationDate())
                .relievingDate(request.getRelievingDate())
                .employmentType(request.getEmploymentType())
                .maritalStatus(request.getMaritalStatus())
                .bloodGroup(request.getBloodGroup())

                .company(company)
                .branch(branch)
                .department(department)
                .designation(designation)
                .role(role)
                .reportingManager(manager)

                .basicSalary(request.getBasicSalary())
                .hra(request.getHra())
                .allowances(request.getAllowances())
                .bonus(request.getBonus())
                .totalSalary(request.getTotalSalary())

                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pinCode(request.getPinCode())

                .panNumber(request.getPanNumber())
                .aadhaarNumber(request.getAadhaarNumber())
                .passportNumber(request.getPassportNumber())
                .drivingLicenseNumber(request.getDrivingLicenseNumber())

                .bankName(request.getBankName())
                .accountNumber(request.getAccountNumber())
                .ifscCode(request.getIfscCode())
                .accountHolderName(request.getAccountHolderName())

                .emergencyContactName(request.getEmergencyContactName())
                .emergencyContactNumber(request.getEmergencyContactNumber())
                .emergencyRelationship(request.getEmergencyRelationship())

                .active(request.getActive() == null ? true : request.getActive())

                .build();
    }

    public void updateEntity(Employee employee,
                             EmployeeRequest request,
                             Company company,
                             Branch branch,
                             Department department,
                             Designation designation,
                             Role role,
                             Employee manager) {

        employee.setFirstName(request.getFirstName());
        employee.setMiddleName(request.getMiddleName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setMobileNumber(request.getMobileNumber());
        employee.setGender(request.getGender());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setConfirmationDate(request.getConfirmationDate());
        employee.setRelievingDate(request.getRelievingDate());
        employee.setEmploymentType(request.getEmploymentType());
        employee.setMaritalStatus(request.getMaritalStatus());
        employee.setBloodGroup(request.getBloodGroup());

        employee.setCompany(company);
        employee.setBranch(branch);
        employee.setDepartment(department);
        employee.setDesignation(designation);
        employee.setRole(role);
        employee.setReportingManager(manager);

        employee.setBasicSalary(request.getBasicSalary());
        employee.setHra(request.getHra());
        employee.setAllowances(request.getAllowances());
        employee.setBonus(request.getBonus());
        employee.setTotalSalary(request.getTotalSalary());

        employee.setAddressLine1(request.getAddressLine1());
        employee.setAddressLine2(request.getAddressLine2());
        employee.setCity(request.getCity());
        employee.setState(request.getState());
        employee.setCountry(request.getCountry());
        employee.setPinCode(request.getPinCode());

        employee.setPanNumber(request.getPanNumber());
        employee.setAadhaarNumber(request.getAadhaarNumber());
        employee.setPassportNumber(request.getPassportNumber());
        employee.setDrivingLicenseNumber(request.getDrivingLicenseNumber());

        employee.setBankName(request.getBankName());
        employee.setAccountNumber(request.getAccountNumber());
        employee.setIfscCode(request.getIfscCode());
        employee.setAccountHolderName(request.getAccountHolderName());

        employee.setEmergencyContactName(request.getEmergencyContactName());
        employee.setEmergencyContactNumber(request.getEmergencyContactNumber());
        employee.setEmergencyRelationship(request.getEmergencyRelationship());

        employee.setActive(request.getActive());
    }

    public EmployeeResponse toResponse(Employee employee) {

        System.out.println("Employee ID: " + employee.getId());
        System.out.println("Employee Code: " + employee.getEmployeeCode());
        System.out.println("Company: " + employee.getCompany());
        System.out.println("Branch: " + employee.getBranch());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("Designation: " + employee.getDesignation());
        System.out.println("Role: " + employee.getRole());

        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())

                .firstName(employee.getFirstName())
                .middleName(employee.getMiddleName())
                .lastName(employee.getLastName())
                .fullName(employee.getFirstName() + " " + employee.getLastName())

                .email(employee.getEmail())
                .mobileNumber(employee.getMobileNumber())
                .gender(employee.getGender())
                .dateOfBirth(employee.getDateOfBirth())
                .joiningDate(employee.getJoiningDate())
                .confirmationDate(employee.getConfirmationDate())
                .relievingDate(employee.getRelievingDate())
                .employmentType(employee.getEmploymentType())
                .maritalStatus(employee.getMaritalStatus())
                .bloodGroup(employee.getBloodGroup())

                .companyId(employee.getCompany().getId())
                .companyName(employee.getCompany().getCompanyName())

                .branchId(employee.getBranch().getId())
                .branchName(employee.getBranch().getBranchName())

                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getDepartmentName())

                .designationId(employee.getDesignation().getId())
                .designationName(employee.getDesignation().getDesignationName())

                .roleId(employee.getRole().getId())
                .roleName(employee.getRole().getRoleName())

                .reportingManagerId(
                        employee.getReportingManager() != null ?
                                employee.getReportingManager().getId() : null)

                .reportingManagerName(
                        employee.getReportingManager() != null ?
                                employee.getReportingManager().getFirstName() + " " +
                                        employee.getReportingManager().getLastName() : null)

                .basicSalary(employee.getBasicSalary())
                .hra(employee.getHra())
                .allowances(employee.getAllowances())
                .bonus(employee.getBonus())
                .totalSalary(employee.getTotalSalary())

                .addressLine1(employee.getAddressLine1())
                .addressLine2(employee.getAddressLine2())
                .city(employee.getCity())
                .state(employee.getState())
                .country(employee.getCountry())
                .pinCode(employee.getPinCode())

                .bankName(employee.getBankName())
                .accountNumber(employee.getAccountNumber())
                .ifscCode(employee.getIfscCode())

                .active(employee.getActive())

                .createdDate(employee.getCreatedDate())
                .updatedDate(employee.getUpdatedDate())

                .build();
    }
}