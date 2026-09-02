package com.CRM.repository;

import com.CRM.entity.LeadActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeadActivityTypeRepository extends JpaRepository<LeadActivityType, Long> {

    Optional<LeadActivityType> findByActivityCode(String activityCode);

    Optional<LeadActivityType> findByActivityNameIgnoreCase(String activityName);

    boolean existsByActivityNameIgnoreCase(String activityName);

    long countByActiveTrue();

}