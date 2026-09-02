package com.CRM.config;

import com.CRM.seed.BranchSeeder;
import com.CRM.seed.CompanySeeder;
import com.CRM.seed.DepartmentSeeder;
import com.CRM.seed.DesignationSeeder;
import com.CRM.seed.EmployeeSeeder;
//import com.CRM.seed.LeadMasterSeeder;
import com.CRM.seed.RoleSeeder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CompanySeeder companySeeder;
    private final BranchSeeder branchSeeder;
    private final DepartmentSeeder departmentSeeder;
    private final DesignationSeeder designationSeeder;
    private final RoleSeeder roleSeeder;
    private final EmployeeSeeder employeeSeeder;
//    private final LeadMasterSeeder leadMasterSeeder;

    @Override
    public void run(String... args) {

        roleSeeder.seed();

        companySeeder.seed();

        branchSeeder.seed();

        departmentSeeder.seed();

        designationSeeder.seed();

        employeeSeeder.seed();

//        leadMasterSeeder.seed();

        System.out.println("========================================");
        System.out.println(" CRM Demo Data Loaded Successfully");
        System.out.println("========================================");
    }
}