package com.CRM.repository;

import com.CRM.entity.Branch;
import com.CRM.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleCode(String roleCode);

    boolean existsByRoleName(String roleName);

    Optional<Role> findByRoleCode(String roleCode);
}
