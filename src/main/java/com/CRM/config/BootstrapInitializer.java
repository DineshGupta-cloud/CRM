package com.CRM.config;

import com.CRM.entity.Role;
import com.CRM.entity.User;
import com.CRM.repository.RoleRepository;
import com.CRM.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) {
            return;
        }

        Role adminRole = roleRepository.findByRoleCode("ROLE001")
                .orElseThrow(() ->
                        new RuntimeException("Admin role (ROLE001) not found."));

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("Admin@123"))
                .email("admin@crm.com")
                .role(adminRole)
                .employee(null)
                .active(true)
                .deleted(false)
                .build();

        userRepository.save(admin);

        log.info("==============================================");
        log.info("Default Admin User Created");
        log.info("Username : admin");
        log.info("Password : Admin@123");
        log.info("==============================================");
    }
}