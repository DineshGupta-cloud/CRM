package com.CRM.repository;

import com.CRM.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByIdAndDeletedFalse(Long id);

    Optional<Employee> findByCompanyIdAndEmployeeCodeAndDeletedFalse(
            Long companyId,
            String employeeCode
    );

    Optional<Employee> findByCompanyIdAndEmailAndDeletedFalse(
            Long companyId,
            String email
    );

    boolean existsByCompanyIdAndEmployeeCodeAndDeletedFalse(
            Long companyId,
            String employeeCode
    );

    boolean existsByCompanyIdAndEmailAndDeletedFalse(
            Long companyId,
            String email
    );

    boolean existsByCompanyIdAndMobileNumberAndDeletedFalse(
            Long companyId,
            String mobileNumber
    );

    List<Employee> findByCompanyIdAndDeletedFalseOrderByIdDesc(
            Long companyId
    );

    Page<Employee> findByCompanyIdAndDeletedFalse(
            Long companyId,
            Pageable pageable
    );

    long countByCompanyIdAndDeletedFalse(Long companyId);

    long countByCompanyIdAndActiveTrueAndDeletedFalse(Long companyId);

    long countByCompanyIdAndActiveFalseAndDeletedFalse(Long companyId);

    long countByCompanyIdAndJoiningDateAfterAndDeletedFalse(
            Long companyId,
            LocalDate localDate
    );

    List<Employee> findTop5ByCompanyIdAndDeletedFalseOrderByIdDesc(
            Long companyId
    );

    List<Employee> findTop10ByCompanyIdAndDeletedFalseOrderByCreatedDateDesc(
            Long companyId
    );

    @Query("""
        SELECT e
        FROM Employee e
        WHERE e.id = :employeeId
          AND e.company.id = :companyId
          AND e.deleted = false
    """)
    Optional<Employee> findScopedEmployee(
            @Param("employeeId") Long employeeId,
            @Param("companyId") Long companyId
    );
}

