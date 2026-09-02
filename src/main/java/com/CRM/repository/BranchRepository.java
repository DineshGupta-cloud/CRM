package com.CRM.repository;

import com.CRM.entity.Branch;
import com.CRM.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    boolean existsByBranchCode(String branchCode);
    long count();

    List<Branch> findByDeletedFalse();

    Optional<Branch> findByIdAndDeletedFalse(Long id);

    boolean existsByBranchCodeAndDeletedFalse(String companyCode);

    boolean existsByBranchNameAndDeletedFalse(String companyName);

    Optional<Branch>  findByBranchCode(String companyCode);


}
