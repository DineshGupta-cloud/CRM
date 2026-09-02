package com.CRM.seed;

import com.CRM.entity.*;
import com.CRM.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeSeeder {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public void seed() {

        if (employeeRepository.count() > 0) {
            System.out.println("✔ Employees already seeded.");
            return;
        }

        Company company =
                companyRepository.findByCompanyCode("CMP001").orElseThrow();

        Branch branch =
                branchRepository.findByBranchCode("BR001").orElseThrow();

        Department department =
                departmentRepository.findByDepartmentCode("DEP009").orElseThrow();

        Designation designation =
                designationRepository.findByDesignationCode("DES015").orElseThrow();

        Role role = roleRepository.findByRoleCode("ROLE006").orElseThrow();

        createEmployee(
                "EMP001",
                "Dinesh",
                "Gupta",
                "dinesh.gupta@crm.com",
                "9876543210",
                company,
                branch,
                department,
                designation,
                role
        );

        createEmployee(
                "EMP002",
                "Rahul",
                "Sharma",
                "rahul.sharma@crm.com",
                "9876543211",
                company,
                branch,
                department,
                designation,
                role
        );

        createEmployee(
                "EMP003",
                "Amit",
                "Verma",
                "amit.verma@crm.com",
                "9876543212",
                company,
                branch,
                department,
                designation,
                role
        );

        System.out.println("✔ Employees Seeded Successfully.");
    }

    private void createEmployee(
            String employeeCode,
            String firstName,
            String lastName,
            String email,
            String mobile,
            Company company,
            Branch branch,
            Department department,
            Designation designation,
            Role role) {

        // Next Part
    }

}