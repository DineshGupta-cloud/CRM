package com.CRM.seed;

import com.CRM.entity.Branch;
import com.CRM.entity.Company;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.repository.BranchRepository;
import com.CRM.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BranchSeeder {
//
//    private final BranchRepository branchRepository;
//    private final CompanyRepository companyRepository;
//
//    public void seed() {
//
//        if (branchRepository.count() > 0) {
//            System.out.println("✔ Branches already seeded.");
//            return;
//        }
//
////        Company cmp1 = getCompany("CMP001");
////        Company cmp2 = getCompany("CMP002");
////        Company cmp3 = getCompany("CMP003");
////        Company cmp4 = getCompany("CMP004");
////        Company cmp5 = getCompany("CMP005");
//
//        List<Branch> branches = new ArrayList<>();
//
//        branches.add(create("BR001", "Pune Head Office", "Pune", cmp1));
//        branches.add(create("BR002", "Mumbai Branch", "Mumbai", cmp1));
//        branches.add(create("BR003", "Nagpur Branch", "Nagpur", cmp1));
//
//        branches.add(create("BR004", "Baner Branch", "Pune", cmp2));
//        branches.add(create("BR005", "Delhi Branch", "New Delhi", cmp2));
//        branches.add(create("BR006", "Noida Branch", "Noida", cmp2));
//
//        branches.add(create("BR007", "Andheri Branch", "Mumbai", cmp3));
//        branches.add(create("BR008", "Ahmedabad Branch", "Ahmedabad", cmp3));
//        branches.add(create("BR009", "Indore Branch", "Indore", cmp3));
//
//        branches.add(create("BR010", "Electronic City Branch", "Bengaluru", cmp4));
//        branches.add(create("BR011", "Chennai Branch", "Chennai", cmp4));
//        branches.add(create("BR012", "Kochi Branch", "Kochi", cmp4));
//
//        branches.add(create("BR013", "Hyderabad Head Office", "Hyderabad", cmp5));
//        branches.add(create("BR014", "Jaipur Branch", "Jaipur", cmp5));
//        branches.add(create("BR015", "Surat Branch", "Surat", cmp5));
//
//        branchRepository.saveAll(branches);
//
//        System.out.println("✔ 15 Branches Seeded Successfully.");
//    }
//
////    private Company getCompany(String companyCode) {
////
////        return companyRepository.findByCompanyCode(companyCode)
////                .orElseThrow(() ->
////                        new ResourceNotFoundException(
////                                "Company not found : " + companyCode));
////    }
//
//    private Branch create(String code,
//                          String name,
//                          String city,
//                          Company company) {
//
//        return Branch.builder()
//                .branchCode(code)
//                .branchName(name)
//                .city(city)
//                .company(company)
//                .active(true)
//                .deleted(false)
//                .build();
//    }

}