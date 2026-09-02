package com.CRM.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddressResponse {

    private Long id;

    private String addressCode;

    private String customerCode;

    private String customerName;

    private String addressType;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private Boolean primaryAddress;

    private Boolean active;
}