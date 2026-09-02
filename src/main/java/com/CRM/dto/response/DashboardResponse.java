package com.CRM.dto.dashboard;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Long totalEmployees;

    private Long activeEmployees;

    private Long inactiveEmployees;

    private Long totalCompanies;

    private Long totalBranches;

    private Long totalDepartments;

    private Long totalDesignations;

    private Long totalUsers;

    private List<com.CRM.dto.dashboard.RecentEmployeeResponse> recentEmployees;

}