package com.CRM.repository;

import com.CRM.entity.CustomerActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerActivityTypeRepository extends JpaRepository<CustomerActivityType, Long> {

    Optional<CustomerActivityType> findByActivityCode(String activityCode);

    Optional<CustomerActivityType> findByActivityName(String activityName);

}