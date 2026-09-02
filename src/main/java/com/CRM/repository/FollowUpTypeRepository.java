package com.CRM.repository;

import com.CRM.entity.FollowUpType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowUpTypeRepository extends JpaRepository<FollowUpType, Long> {

    Optional<FollowUpType> findByTypeCode(String typeCode);

    Optional<FollowUpType> findByTypeNameIgnoreCase(String typeName);

    boolean existsByTypeNameIgnoreCase(String typeName);

    long countByActiveTrue();

}