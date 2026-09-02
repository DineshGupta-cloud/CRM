package com.CRM.repository;

import com.CRM.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

    Optional<Opportunity> findTopByOrderByIdDesc();

    List<Opportunity> findByDeletedFalseOrderByCreatedDateDesc();

    List<Opportunity> findByCustomerIdAndDeletedFalseOrderByCreatedDateDesc(Long customerId);

//    boolean existsByOpportunityNameIgnoreCase(String opportunityName);
    Optional<Opportunity> findByOpportunityNameIgnoreCase(String opportunityName);
}