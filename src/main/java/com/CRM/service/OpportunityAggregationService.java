package com.CRM.service;

import java.math.BigDecimal;

public interface OpportunityAggregationService {

    void recalculateOpportunityValue(Long opportunityId);

    BigDecimal calculateTotalValue(Long opportunityId);
}