package com.CRM.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityProductResponse {

    private Long id;

    private String productName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal discount;

    private BigDecimal totalAmount;
}