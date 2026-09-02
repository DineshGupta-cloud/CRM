package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddressRequest {

    @NotNull(message = "Customer is required.")
    private Long customerId;

    @NotBlank(message = "Address type is required.")
    private String addressType;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    @Builder.Default
    private Boolean primaryAddress = false;
}