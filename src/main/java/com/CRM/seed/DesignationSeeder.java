package com.CRM.seed;

import com.CRM.entity.Designation;
import com.CRM.repository.DesignationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DesignationSeeder {

    private final DesignationRepository designationRepository;

    public void seed() {

        if (designationRepository.count() > 0) {
            System.out.println("✔ Designations already seeded.");
            return;
        }

        List<Designation> designations = List.of(

                create("DES001", "Chairman"),
                create("DES002", "Managing Director"),
                create("DES003", "Chief Executive Officer"),
                create("DES004", "Chief Technology Officer"),
                create("DES005", "Chief Financial Officer"),

                create("DES006", "General Manager"),
                create("DES007", "Project Manager"),
                create("DES008", "HR Manager"),
                create("DES009", "Sales Manager"),
                create("DES010", "Marketing Manager"),

                create("DES011", "Finance Manager"),
                create("DES012", "Operations Manager"),
                create("DES013", "Branch Manager"),
                create("DES014", "Team Lead"),
                create("DES015", "Senior Software Engineer"),

                create("DES016", "Software Engineer"),
                create("DES017", "Senior QA Engineer"),
                create("DES018", "QA Engineer"),
                create("DES019", "DevOps Engineer"),
                create("DES020", "Business Analyst"),

                create("DES021", "System Administrator"),
                create("DES022", "Database Administrator"),
                create("DES023", "UI/UX Designer"),
                create("DES024", "Technical Support Engineer"),
                create("DES025", "Customer Support Executive"),

                create("DES026", "Sales Executive"),
                create("DES027", "Marketing Executive"),
                create("DES028", "Accountant"),
                create("DES029", "Office Administrator"),
                create("DES030", "Receptionist"),

                create("DES031", "Office Assistant"),
                create("DES032", "HR Executive"),
                create("DES033", "Finance Executive"),
                create("DES034", "Intern"),
                create("DES035", "Consultant")

        );

        designationRepository.saveAll(designations);

        System.out.println("✔ 35 Designations Seeded Successfully.");
    }

    private Designation create(String code, String name) {

        return Designation.builder()
                .designationCode(code)
                .designationName(name)
                .active(true)
                .deleted(false)
                .build();
    }
}