package com.CRM.repository;

import com.CRM.entity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerContactRepository extends JpaRepository<CustomerContact, Long> {

    List<CustomerContact> findByCustomerIdAndDeletedFalseOrderByFirstNameAsc(Long customerId);

    Optional<CustomerContact> findTopByOrderByIdDesc();

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    Optional<CustomerContact> findByEmail(String email);

    Optional<CustomerContact> findByMobile(String mobile);

}