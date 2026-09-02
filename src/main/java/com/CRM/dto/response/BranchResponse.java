package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponse {

    private Long id;

    private String branchCode;

    private String branchName;

    private Long companyId;

    private String companyName;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private String phone;

    private String email;

    private Boolean active;
}