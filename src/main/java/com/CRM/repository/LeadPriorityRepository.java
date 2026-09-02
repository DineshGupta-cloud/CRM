package com.CRM.repository;

import com.CRM.entity.LeadPriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeadPriorityRepository extends JpaRepository<LeadPriority, Long> {

    Optional<LeadPriority> findByPriorityCode(String priorityCode);

    Optional<LeadPriority> findByPriorityNameIgnoreCase(String priorityName);

    boolean existsByPriorityNameIgnoreCase(String priorityName);

    long countByActiveTrue();
}