package com.CRM.security;

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

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            return null;
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Logged-in user not found."));
    }

    public Employee getCurrentEmployee() {

        User user = getCurrentUser();

        return user != null ? user.getEmployee() : null;
    }

    public  Long getCurrentEmployeeId() {

        Employee employee = getCurrentEmployee();

        return employee != null ? employee.getId() : null;
    }

    public Long getCurrentUserId() {

        User user = getCurrentUser();

        return user != null ? user.getId() : null;
    }

    public String getCurrentUsername() {

        User user = getCurrentUser();

        return user != null ? user.getUsername() : null;
    }

    public boolean hasRole(String role) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
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

    public boolean isSales() {
        return hasRole("SALES");
    }

    public boolean isSupport() {
        return hasRole("SUPPORT");
    }
}