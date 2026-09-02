package com.CRM.dto.dashboard;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentEmployeeResponse {

    private Long id;

    private String employeeCode;

    private String employeeName;

    private String department;

    private String designation;

}