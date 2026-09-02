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

@Component
@RequiredArgsConstructor
@Slf4j
public class MasterDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        createDefaultRoles();

        createDefaultAdmin();

    }

    /**
     * Create default roles only if role table is empty.
     */
    private void createDefaultRoles() {

        if (roleRepository.count() > 0) {
            return;
        }

        log.info("Creating default roles...");

        roleRepository.save(Role.builder()
                .roleCode("ROLE001")
                .roleName("Administrator")
                .description("System Administrator")
                .active(true)
                .build());

        roleRepository.save(Role.builder()
                .roleCode("ROLE002")
                .roleName("Human Resource")
                .description("Human Resource")
                .active(true)
                .build());

        roleRepository.save(Role.builder()
                .roleCode("ROLE003")
                .roleName("Manager")
                .description("Manager")
                .active(true)
                .build());

        roleRepository.save(Role.builder()
                .roleCode("ROLE004")
                .roleName("Team Lead")
                .description("Team Lead")
                .active(true)
                .build());

        roleRepository.save(Role.builder()
                .roleCode("ROLE005")
                .roleName("Employee")
                .description("Employee")
                .active(true)
                .build());

        roleRepository.save(Role.builder()
                .roleCode("ROLE006")
                .roleName("Sales")
                .description("Sales Department")
                .active(true)
                .build());

        roleRepository.save(Role.builder()
                .roleCode("ROLE007")
                .roleName("Finance")
                .description("Finance Department")
                .active(true)
                .build());

        roleRepository.save(Role.builder()
                .roleCode("ROLE008")
                .roleName("Support")
                .description("Customer Support")
                .active(true)
                .build());

        log.info("Default roles created.");
    }

    /**
     * Create first admin user only if no users exist.
     */
    private void createDefaultAdmin() {

        if (userRepository.count() > 0) {
            return;
        }

        log.info("Creating default admin user...");

        Role adminRole = roleRepository.findByRoleCode("ROLE001")
                .orElseThrow(() ->
                        new RuntimeException("Admin role not found."));

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

        log.info("===========================================");
        log.info("Default Admin Created");
        log.info("Username : admin");
        log.info("Password : Admin@123");
        log.info("===========================================");
    }
}