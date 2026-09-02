package com.CRM.seed;

import com.CRM.entity.Company;
import com.CRM.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanySeeder {

    private final CompanyRepository companyRepository;

    public void seed() {

        if (companyRepository.count() > 0) {
            System.out.println("✔ Companies already seeded.");
            return;
        }

        List<Company> companies = List.of(

                Company.builder()
                        .companyCode("CMP001")
                        .companyName("VGI Solutions Pvt. Ltd.")
                        .email("info@vgisolutions.com")
                        .phone("02040010001")
                        .website("https://www.vgisolutions.com")
                        .gstNumber("27ABCDE1234F1Z5")
                        .panNumber("ABCDE1234F")
                        .address("Hinjewadi Phase 1")
                        .city("Pune")
                        .state("Maharashtra")
                        .country("India")
                        .active(true)
                        .deleted(false)
                        .build(),

                Company.builder()
                        .companyCode("CMP002")
                        .companyName("TechNova Solutions Pvt. Ltd.")
                        .email("contact@technova.in")
                        .phone("02040010002")
                        .website("https://www.technova.in")
                        .gstNumber("27ABCDE1234F2Z5")
                        .panNumber("ABCDE1234G")
                        .address("Baner Road")
                        .city("Pune")
                        .state("Maharashtra")
                        .country("India")
                        .active(true)
                        .deleted(false)
                        .build(),

                Company.builder()
                        .companyCode("CMP003")
                        .companyName("Global Infosystems Pvt. Ltd.")
                        .email("info@globalinfo.in")
                        .phone("02240010003")
                        .website("https://www.globalinfo.in")
                        .gstNumber("27ABCDE1234F3Z5")
                        .panNumber("ABCDE1234H")
                        .address("Andheri East")
                        .city("Mumbai")
                        .state("Maharashtra")
                        .country("India")
                        .active(true)
                        .deleted(false)
                        .build(),

                Company.builder()
                        .companyCode("CMP004")
                        .companyName("Smart Business Solutions Pvt. Ltd.")
                        .email("sales@smartbusiness.in")
                        .phone("08040010004")
                        .website("https://www.smartbusiness.in")
                        .gstNumber("29ABCDE1234F4Z5")
                        .panNumber("ABCDE1234J")
                        .address("Electronic City")
                        .city("Bengaluru")
                        .state("Karnataka")
                        .country("India")
                        .active(true)
                        .deleted(false)
                        .build(),

                Company.builder()
                        .companyCode("CMP005")
                        .companyName("NextGen Technologies Pvt. Ltd.")
                        .email("support@nextgen.in")
                        .phone("04040010005")
                        .website("https://www.nextgen.in")
                        .gstNumber("36ABCDE1234F5Z5")
                        .panNumber("ABCDE1234K")
                        .address("Hitech City")
                        .city("Hyderabad")
                        .state("Telangana")
                        .country("India")
                        .active(true)
                        .deleted(false)
                        .build()

        );

        companyRepository.saveAll(companies);

        System.out.println("✔ 5 Companies Seeded Successfully.");
    }
}