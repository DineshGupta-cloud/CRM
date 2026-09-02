package com.CRM.repository;

import com.CRM.entity.OpportunityStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OpportunityStageRepository extends JpaRepository<OpportunityStage, Long> {

    List<OpportunityStage> findByDeletedFalseOrderByDisplayOrderAsc();

    Optional<OpportunityStage> findByStageNameIgnoreCase(String stageName);

    Optional<OpportunityStage> findTopByOrderByIdDesc();
}