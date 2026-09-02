package com.CRM.repository;

import com.CRM.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long> {

    Optional<Industry> findByIndustryCode(String industryCode);

    Optional<Industry> findByIndustryNameIgnoreCase(String industryName);

    boolean existsByIndustryNameIgnoreCase(String industryName);

    long countByActiveTrue();

}