package com.CRM.security;

import com.CRM.entity.Branch;
import com.CRM.entity.Company;
import com.CRM.entity.Employee;
import com.CRM.entity.User;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;

    // =========================================================
    // AUTHENTICATED USER
    // =========================================================

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new ResourceNotFoundException(
                    "Authenticated user not found."
            );
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Logged-in user not found."
                        ));
    }

    // =========================================================
    // CURRENT EMPLOYEE
    // =========================================================

    public Employee getCurrentEmployee() {

        User user = getCurrentUser();

        if (user.getEmployee() == null) {
            throw new ResourceNotFoundException(
                    "Employee profile is not linked to the logged-in user."
            );
        }

        return user.getEmployee();
    }

    public Long getCurrentEmployeeId() {
        return getCurrentEmployee().getId();
    }

    // =========================================================
    // CURRENT USER ID
    // =========================================================

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }

    // =========================================================
    // COMPANY SCOPE
    // =========================================================

    public Company getCurrentCompany() {

        Employee employee = getCurrentEmployee();

        if (employee.getCompany() == null) {
            throw new ResourceNotFoundException(
                    "Company is not assigned to the logged-in employee."
            );
        }

        return employee.getCompany();
    }

    public Long getCurrentCompanyId() {
        return getCurrentCompany().getId();
    }

    // =========================================================
    // BRANCH SCOPE
    // =========================================================

    public Branch getCurrentBranch() {

        Employee employee = getCurrentEmployee();

        if (employee.getBranch() == null) {
            throw new ResourceNotFoundException(
                    "Branch is not assigned to the logged-in employee."
            );
        }

        return employee.getBranch();
    }

    public Long getCurrentBranchId() {
        return getCurrentBranch().getId();
    }

    // =========================================================
    // ROLE
    // =========================================================

    public boolean hasRole(String role) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {
            return false;
        }

        String expectedAuthority = "ROLE_" + role.toUpperCase();

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority()
                                .equals(expectedAuthority)
                );
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isHR() {
        return hasRole("HR");
    }

    public boolean isManager() {
        return hasRole("MANAGER");
    }

    public boolean isTeamLead() {
        return hasRole("TEAM_LEAD");
    }

    public boolean isEmployee() {
        return hasRole("EMPLOYEE");
    }

    public boolean isSales() {
        return hasRole("SALES");
    }

    public boolean isFinance() {
        return hasRole("FINANCE");
    }

    public boolean isSupport() {
        return hasRole("SUPPORT");
    }

    // =========================================================
    // SCOPE HELPERS
    // =========================================================

    public boolean isSameCompany(Long companyId) {

        if (companyId == null) {
            return false;
        }

        return companyId.equals(getCurrentCompanyId());
    }

    public boolean isSameBranch(Long branchId) {

        if (branchId == null) {
            return false;
        }

        return branchId.equals(getCurrentBranchId());
    }

    public boolean isCurrentEmployee(Long employeeId) {

        if (employeeId == null) {
            return false;
        }

        return employeeId.equals(getCurrentEmployeeId());
    }
}