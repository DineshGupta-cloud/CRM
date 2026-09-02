package com.CRM.repository;

import com.CRM.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCompanyCode(String companyCode);
    long count();

    List<Company> findByDeletedFalse();

    Optional<Company> findByIdAndDeletedFalse(Long id);

    boolean existsByCompanyCodeAndDeletedFalse(String companyCode);

    boolean existsByCompanyNameAndDeletedFalse(String companyName);
    Optional<Company> findByCompanyCode(String companyCode);
}
