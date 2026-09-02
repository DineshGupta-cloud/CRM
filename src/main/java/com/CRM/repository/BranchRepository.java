package com.CRM.repository;

import com.CRM.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByIdAndDeletedFalse(Long id);

    Optional<Branch> findByIdAndCompanyIdAndDeletedFalse(
            Long id,
            Long companyId
    );

    List<Branch> findByCompanyIdAndDeletedFalseOrderByIdDesc(
            Long companyId
    );

    boolean existsByCompanyIdAndBranchCodeAndDeletedFalse(
            Long companyId,
            String branchCode
    );

    boolean existsByCompanyIdAndBranchNameAndDeletedFalse(
            Long companyId,
            String branchName
    );

    Optional<Branch> findByCompanyIdAndBranchCodeAndDeletedFalse(
            Long companyId,
            String branchCode
    );

    long countByCompanyIdAndDeletedFalse(Long companyId);

    long countByCompanyIdAndActiveTrueAndDeletedFalse(Long companyId);

    long countByCompanyIdAndActiveFalseAndDeletedFalse(Long companyId);
}