package com.CRM.repository;

import com.CRM.entity.OpportunityActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpportunityActivityRepository extends JpaRepository<OpportunityActivity, Long> {

    List<OpportunityActivity> findByOpportunityIdOrderByCreatedDateDesc(Long opportunityId);
}