package com.CRM.repository;

import com.CRM.entity.LeadActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadActivityRepository
        extends JpaRepository<LeadActivity,Long> {

    List<LeadActivity> findByLeadIdOrderByCreatedDateDesc(Long leadId);

    List<LeadActivity> findByPerformedById(Long employeeId);

    long countByActiveTrue();

}