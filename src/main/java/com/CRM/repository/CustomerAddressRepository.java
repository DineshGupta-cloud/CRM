package com.CRM.repository;

import com.CRM.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

    List<CustomerAddress> findByCustomerIdAndDeletedFalseOrderByAddressTypeAsc(Long customerId);

    Optional<CustomerAddress> findTopByOrderByIdDesc();

}