package com.CRM.repository;

import com.CRM.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByIdAndDeletedFalse(Long id);

    Optional<Company> findByCompanyCodeAndDeletedFalse(String companyCode);

    boolean existsByCompanyCodeAndDeletedFalse(String companyCode);

    boolean existsByCompanyNameAndDeletedFalse(String companyName);

    List<Company> findByDeletedFalse();

    long countByDeletedFalse();
}