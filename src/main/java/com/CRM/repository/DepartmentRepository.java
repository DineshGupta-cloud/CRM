package com.CRM.repository;

import com.CRM.entity.Branch;
import com.CRM.entity.Company;
import com.CRM.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentCode(String departmentCode);
    long count();

    Optional<Department> findByIdAndBranchIdAndDeletedFalse(Long id, Long branchId);

    List<Department> findByDeletedFalse();

    Optional<Department> findByIdAndDeletedFalse(Long id);

    boolean existsByDepartmentCodeAndDeletedFalse(String companyCode);

    boolean existsByDepartmentNameAndDeletedFalse(String companyName);
    Optional<Department> findByDepartmentCode(String departmentCode);

}
