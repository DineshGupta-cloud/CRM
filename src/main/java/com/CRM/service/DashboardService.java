package com.CRM.service;

import com.CRM.dto.DashboardSummaryDto;
import com.CRM.dto.EmployeeDto;
import com.CRM.dto.dashboard.DashboardResponse;
import com.CRM.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DashboardService {

    DashboardSummaryDto getDashboardDetails();

    List<EmployeeDto> findTop5ByOrderByIdDesc();

    DashboardResponse getDashboard();

}
