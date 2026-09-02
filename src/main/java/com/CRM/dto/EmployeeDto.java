package com.CRM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String employeeCode;
    private String firstName;
    private String lastName;
    private Boolean status = true;
}
