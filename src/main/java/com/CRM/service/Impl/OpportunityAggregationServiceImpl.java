package com.CRM.service.impl;

import com.CRM.entity.Opportunity;
import com.CRM.entity.OpportunityProduct;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.repository.OpportunityProductRepository;
import com.CRM.repository.OpportunityRepository;
import com.CRM.service.OpportunityAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityAggregationServiceImpl implements OpportunityAggregationService {

    private final OpportunityRepository opportunityRepository;
    private final OpportunityProductRepository productRepository;

    @Override
    public void recalculateOpportunityValue(Long opportunityId) {

        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opportunity not found."));

        BigDecimal total = calculateTotalValue(opportunityId);

        opportunity.setExpectedRevenue(total);

        opportunityRepository.save(opportunity);
    }

    @Override
    public BigDecimal calculateTotalValue(Long opportunityId) {

        List<OpportunityProduct> products =
                productRepository.findByOpportunityIdAndDeletedFalse(opportunityId);

        return products.stream()
                .map(p -> p.getTotalAmount() == null
                        ? BigDecimal.ZERO
                        : p.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}