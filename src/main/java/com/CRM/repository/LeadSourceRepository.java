package com.CRM.repository;

import com.CRM.entity.LeadSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeadSourceRepository extends JpaRepository<LeadSource, Long> {

    Optional<LeadSource> findBySourceCode(String sourceCode);

    Optional<LeadSource> findBySourceNameIgnoreCase(String sourceName);

    boolean existsBySourceNameIgnoreCase(String sourceName);

    long countByActiveTrue();

}