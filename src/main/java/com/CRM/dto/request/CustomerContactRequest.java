package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerContactRequest {

    @NotNull(message = "Customer is required.")
    private Long customerId;

    @NotBlank(message = "First name is required.")
    private String firstName;

    private String lastName;

    private String designation;

    private String department;

    private String email;

    private String mobile;

    private String phone;

    @Builder.Default
    private Boolean primaryContact = false;
}