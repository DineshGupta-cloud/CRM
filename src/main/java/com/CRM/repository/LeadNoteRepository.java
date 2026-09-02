package com.CRM.repository;

import com.CRM.entity.LeadNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadNoteRepository extends JpaRepository<LeadNote, Long> {

    List<LeadNote> findByLeadIdOrderByCreatedDateDesc(Long leadId);

    long countByActiveTrue();
}