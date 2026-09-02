package com.CRM.repository;

import com.CRM.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);


    Optional<Employee> findByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);

    boolean existsByEmployeeCode(String employeeCode);

    Employee findTopByOrderByIdDesc();
    long countByActive(Boolean  status);

     long countByJoiningDateAfter(LocalDate localDate);

    List<Employee> findTop5ByOrderByIdDesc();

    long countByActiveTrue();

    long countByActiveFalse();
    List<Employee> findTop10ByOrderByCreatedDateDesc();

}
