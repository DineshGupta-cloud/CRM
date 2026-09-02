package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

    private Long id;

    private String companyCode;

    private String companyName;

    private String email;

    private String phone;

    private String website;

    private String gstNumber;

    private String panNumber;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private Boolean active;
}