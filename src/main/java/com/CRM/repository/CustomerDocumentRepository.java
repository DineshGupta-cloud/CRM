package com.CRM.repository;

import com.CRM.entity.CustomerDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, Long> {

    List<CustomerDocument> findByCustomerIdAndDeletedFalseOrderByCreatedDateDesc(Long customerId);

    Optional<CustomerDocument> findTopByOrderByIdDesc();

}