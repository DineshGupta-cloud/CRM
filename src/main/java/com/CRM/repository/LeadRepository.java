package com.CRM.repository;

import com.CRM.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    Optional<Lead> findByLeadCode(String leadCode);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByMobileAndIdNot(String mobile, Long id);

    Optional<Lead> findByEmail(String email);

    Optional<Lead> findByMobile(String mobile);

    long countByActiveTrue();

}