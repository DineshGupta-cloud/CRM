package com.CRM.mapper;

import com.CRM.dto.request.OpportunityProductRequest;
import com.CRM.dto.response.OpportunityProductResponse;
import com.CRM.entity.Opportunity;
import com.CRM.entity.OpportunityProduct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OpportunityProductMapper {

    public OpportunityProduct toEntity(OpportunityProductRequest request,
                                       Opportunity opportunity) {

        BigDecimal total = calculateTotal(
                request.getQuantity(),
                request.getUnitPrice(),
                request.getDiscount()
        );

        return OpportunityProduct.builder()
                .productName(request.getProductName())
                .opportunity(opportunity)
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .discount(request.getDiscount())
                .totalAmount(total)
                .build();
    }

    public void updateEntity(OpportunityProduct product,
                             OpportunityProductRequest request) {

        product.setProductName(request.getProductName());
        product.setQuantity(request.getQuantity());
        product.setUnitPrice(request.getUnitPrice());
        product.setDiscount(request.getDiscount());

        product.setTotalAmount(
                calculateTotal(
                        request.getQuantity(),
                        request.getUnitPrice(),
                        request.getDiscount()
                )
        );
    }

    public OpportunityProductResponse toResponse(OpportunityProduct product) {

        return OpportunityProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .unitPrice(product.getUnitPrice())
                .discount(product.getDiscount())
                .totalAmount(product.getTotalAmount())
                .build();
    }

    private BigDecimal calculateTotal(Integer qty,
                                      BigDecimal price,
                                      BigDecimal discount) {

        BigDecimal qtyVal = BigDecimal.valueOf(qty);
        BigDecimal base = price.multiply(qtyVal);

        if (discount == null) return base;

        return base.subtract(discount);
    }
}