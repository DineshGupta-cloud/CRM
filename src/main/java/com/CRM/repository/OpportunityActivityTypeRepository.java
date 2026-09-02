package com.CRM.repository;

import com.CRM.entity.OpportunityActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpportunityActivityTypeRepository extends JpaRepository<OpportunityActivityType, Long> {

    Optional<OpportunityActivityType> findByTypeCode(String typeCode);
}