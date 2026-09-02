package com.CRM.seed;

import com.CRM.entity.Department;
import com.CRM.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentSeeder {

    private final DepartmentRepository departmentRepository;

    public void seed() {

        if (departmentRepository.count() > 0) {
            System.out.println("✔ Departments already seeded.");
            return;
        }

        List<Department> departments = List.of(

                create("DEP001", "Administration"),
                create("DEP002", "Human Resources"),
                create("DEP003", "Sales"),
                create("DEP004", "Marketing"),
                create("DEP005", "Finance"),
                create("DEP006", "Accounts"),
                create("DEP007", "Customer Support"),
                create("DEP008", "Operations"),
                create("DEP009", "Information Technology"),
                create("DEP010", "Software Development"),
                create("DEP011", "Quality Assurance"),
                create("DEP012", "DevOps"),
                create("DEP013", "Business Development"),
                create("DEP014", "Legal"),
                create("DEP015", "Purchase"),
                create("DEP016", "Research & Development"),
                create("DEP017", "Training"),
                create("DEP018", "Security"),
                create("DEP019", "Administration Services"),
                create("DEP020", "Management")

        );

        departmentRepository.saveAll(departments);

        System.out.println("✔ 20 Departments Seeded Successfully.");
    }

    private Department create(String code, String name) {

        return Department.builder()
                .departmentCode(code)
                .departmentName(name)
                .active(true)
                .deleted(false)
                .build();
    }
}