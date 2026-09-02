package com.CRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "opportunity_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_id")
    private Opportunity opportunity;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal discount;

    private BigDecimal totalAmount;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Boolean deleted = false;
}