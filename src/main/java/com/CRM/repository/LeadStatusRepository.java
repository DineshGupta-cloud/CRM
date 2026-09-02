package com.CRM.repository;

import com.CRM.entity.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeadStatusRepository extends JpaRepository<LeadStatus, Long> {

    Optional<LeadStatus> findByStatusCode(String statusCode);

    Optional<LeadStatus> findByStatusNameIgnoreCase(String statusName);

    boolean existsByStatusNameIgnoreCase(String statusName);

    long countByActiveTrue();
}