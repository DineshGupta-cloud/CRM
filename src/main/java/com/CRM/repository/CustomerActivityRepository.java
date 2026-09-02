package com.CRM.repository;

import com.CRM.entity.CustomerActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerActivityRepository extends JpaRepository<CustomerActivity, Long> {

    List<CustomerActivity> findByCustomerIdOrderByActivityDateDesc(Long customerId);

}