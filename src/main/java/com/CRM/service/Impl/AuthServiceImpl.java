package com.CRM.service.Impl;

import com.CRM.dto.auth.*;
import com.CRM.entity.Employee;
import com.CRM.entity.RefreshToken;
import com.CRM.entity.Role;
import com.CRM.entity.User;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.repository.EmployeeRepository;
import com.CRM.repository.RoleRepository;
import com.CRM.repository.UserRepository;
import com.CRM.security.CustomUserDetails;
import com.CRM.security.JwtService;
import com.CRM.service.AuthService;
import com.CRM.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final EmployeeRepository employeeRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid username or password"));
        System.out.println(user.getUsername());

        CustomUserDetails userDetails = new CustomUserDetails(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        String accessToken =
                jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .employeeId(
                        user.getEmployee() != null
                                ? user.getEmployee().getId()
                                : null
                )
                .fullName(
                        user.getEmployee() != null
                                ? user.getEmployee().getFirstName()
                                + " "
                                + user.getEmployee().getLastName()
                                : user.getUsername()
                )
                .role(user.getRole().getRoleCode())
                .expiresIn(86400L)
                .build();
    }

    @Override
    public void register(RegisterUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

//        Employee employee = employeeRepository.findById(request.getEmployeeId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Employee not found."));

        Employee employee = null;

        if (request.getEmployeeId() != null) {
            employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Employee not found."));
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found."));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .employee(employee)
                .role(role)
                .enabled(request.getEnabled())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .active(true)
                .deleted(false)
                .build();

        userRepository.save(user);
    }

    @Override
    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyExpiration(
                        request.getRefreshToken());

        User user = refreshToken.getUser();

        String accessToken =
                jwtService.generateToken(
                        new CustomUserDetails(user));

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(86400L)
                .build();
    }

    @Override
    public void logout(RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyExpiration(
                        request.getRefreshToken());

        refreshTokenService.deleteByUser(
                refreshToken.getUser());
    }
}