package com.CRM.repository;

import com.CRM.entity.LeadFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeadFollowUpRepository extends JpaRepository<LeadFollowUp, Long> {

    List<LeadFollowUp> findByLeadId(Long leadId);

    List<LeadFollowUp> findByAssignedEmployeeId(Long employeeId);

    List<LeadFollowUp> findByFollowUpDate(LocalDate followUpDate);

    List<LeadFollowUp> findByCompletedFalse();

    List<LeadFollowUp> findByCompletedFalseAndFollowUpDateLessThan(LocalDate date);

    List<LeadFollowUp> findByAssignedEmployeeIdAndCompletedFalse(Long employeeId);

    List<LeadFollowUp> findByAssignedEmployeeIdAndFollowUpDate(Long employeeId,
                                                               LocalDate date);

}