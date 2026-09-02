package com.CRM.seed;

import com.CRM.entity.Role;
import com.CRM.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleSeeder {

    private final RoleRepository roleRepository;

    public void seed() {

        if (roleRepository.count() > 0) {
            System.out.println("✔ Roles already seeded.");
            return;
        }

        List<Role> roles = List.of(

                create("ROLE001", "SUPER_ADMIN", "Super Administrator"),
                create("ROLE002", "ADMIN", "Administrator"),
                create("ROLE003", "COMPANY_ADMIN", "Company Administrator"),
                create("ROLE004", "BRANCH_MANAGER", "Branch Manager"),
                create("ROLE005", "HR_MANAGER", "HR Manager"),
                create("ROLE006", "SALES_MANAGER", "Sales Manager"),
                create("ROLE007", "TEAM_LEAD", "Team Lead"),
                create("ROLE008", "SALES_EXECUTIVE", "Sales Executive"),
                create("ROLE009", "MARKETING_EXECUTIVE", "Marketing Executive"),
                create("ROLE010", "EMPLOYEE", "Employee"),
                create("ROLE011", "CUSTOMER_SUPPORT", "Customer Support"),
                create("ROLE012", "FINANCE", "Finance"),
                create("ROLE013", "ACCOUNTANT", "Accountant"),
                create("ROLE014", "AUDITOR", "Auditor"),
                create("ROLE015", "REPORT_VIEWER", "Report Viewer")

        );

        roleRepository.saveAll(roles);

        System.out.println("✔ 15 Roles Seeded Successfully.");
    }

    private Role create(String code,
                        String name,
                        String displayName) {

        return Role.builder()
                .roleCode(code)
                .roleName(displayName)
//                .roleKey(name)
                .active(true)
//                .deleted(false)
                .build();
    }
}