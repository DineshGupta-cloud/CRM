package com.CRM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryDto {

    private Long totalEmployees;

    private Long activeEmployees;

    private Long inactiveEmployees;

    private Long newEmployees;
}