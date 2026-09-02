package com.CRM.mapper;

import com.CRM.dto.request.DepartmentRequest;
import com.CRM.dto.response.DepartmentResponse;
import com.CRM.entity.Branch;
import com.CRM.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequest request, Branch branch) {

        return Department.builder()
                .departmentCode(request.getDepartmentCode())
                .departmentName(request.getDepartmentName())
                .branch(branch)
                .active(request.getActive() == null ? true : request.getActive())
                .build();
    }

    public DepartmentResponse toResponse(Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .departmentCode(department.getDepartmentCode())
                .departmentName(department.getDepartmentName())
                .branchId(department.getBranch().getId())
                .branchName(department.getBranch().getBranchName())
                .active(department.getActive())
                .build();
    }
}