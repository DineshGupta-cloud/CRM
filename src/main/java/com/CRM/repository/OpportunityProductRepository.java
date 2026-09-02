package com.CRM.repository;

import com.CRM.entity.OpportunityProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpportunityProductRepository extends JpaRepository<OpportunityProduct, Long> {

    List<OpportunityProduct> findByOpportunityIdAndDeletedFalse(Long opportunityId);
}