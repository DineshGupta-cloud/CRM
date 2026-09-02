package com.CRM.repository;

import com.CRM.entity.Branch;
import com.CRM.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {


    boolean existsByDesignationCode(String designationCode);

    boolean existsByDesignationName(String designationName);
    long count();

    List<Designation> findByDeletedFalse();

    Optional<Designation> findByIdAndDeletedFalse(Long id);

    boolean existsByDesignationCodeAndDeletedFalse(String companyCode);
    Optional<Designation>findByDesignationCode(String companyCode);

    boolean existsByDesignationNameAndDeletedFalse(String companyName);
}
