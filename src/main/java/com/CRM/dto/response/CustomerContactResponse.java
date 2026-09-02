package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerContactResponse {

    private Long id;

    private String contactCode;

    private String customerCode;

    private String customerName;

    private String firstName;

    private String lastName;

    private String designation;

    private String department;

    private String email;

    private String mobile;

    private String phone;

    private Boolean primaryContact;

    private Boolean active;
}