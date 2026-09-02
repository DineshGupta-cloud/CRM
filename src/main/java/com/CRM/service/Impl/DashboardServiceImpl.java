package com.CRM.service.Impl;

import com.CRM.dto.DashboardSummaryDto;
import com.CRM.dto.EmployeeDto;
import com.CRM.dto.dashboard.DashboardResponse;
import com.CRM.dto.dashboard.RecentEmployeeResponse;
import com.CRM.entity.Employee;
import com.CRM.repository.*;
import com.CRM.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

//    private EmployeeRepository employeeRepository;



    @Override
    public DashboardSummaryDto getDashboardDetails() {

        long total = employeeRepository.count();
        List<Employee> e = employeeRepository.findAll();


        long active = employeeRepository.countByActive(true);

        long inactive = employeeRepository.countByActive(false);

        long newEmployees = employeeRepository
                .countByJoiningDateAfter(LocalDate.now().minusDays(30));

        return new DashboardSummaryDto(
                total,
                active,
                inactive,
                newEmployees
        );
    }


    @Override
    public List<EmployeeDto> findTop5ByOrderByIdDesc() {

        return employeeRepository
                .findTop5ByOrderByIdDesc()
                .stream()
                .map(employee -> {

                    EmployeeDto dto = new EmployeeDto();

                    dto.setEmployeeCode(employee.getEmployeeCode());
                    dto.setFirstName(employee.getFirstName());
                    dto.setLastName(employee.getLastName());
                    dto.setStatus(employee.getActive());

                    return dto;
                })
                .toList();
    }


    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardResponse getDashboard() {

        List<RecentEmployeeResponse> recentEmployees =
                employeeRepository.findTop10ByOrderByCreatedDateDesc()
                        .stream()
                        .map(this::mapEmployee)
                        .toList();

        return DashboardResponse.builder()
                .totalEmployees(employeeRepository.count())
                .activeEmployees(employeeRepository.countByActiveTrue())
                .inactiveEmployees(employeeRepository.countByActiveFalse())
                .totalCompanies(companyRepository.count())
                .totalBranches(branchRepository.count())
                .totalDepartments(departmentRepository.count())
                .totalDesignations(designationRepository.count())
                .totalUsers(userRepository.count())
                .recentEmployees(recentEmployees)
                .build();
    }

    private RecentEmployeeResponse mapEmployee(Employee employee) {

        return RecentEmployeeResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .employeeName(
                        employee.getFirstName() + " " +
                                employee.getLastName()
                )
                .department(
                        employee.getDepartment() != null
                                ? employee.getDepartment().getDepartmentName()
                                : ""
                )
                .designation(
                        employee.getDesignation() != null
                                ? employee.getDesignation().getDesignationName()
                                : ""
                )
                .build();
    }

}
