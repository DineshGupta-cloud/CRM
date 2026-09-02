package com.CRM.service.Impl;

import com.CRM.dto.request.RoleRequest;
import com.CRM.dto.response.RoleResponse;
import com.CRM.entity.Role;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.RoleMapper;
import com.CRM.repository.RoleRepository;
import com.CRM.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {

        if (roleRepository.existsByRoleCode(request.getRoleCode())) {
            throw new DuplicateResourceException("Role code already exists.");
        }

        if (roleRepository.existsByRoleName(request.getRoleName())) {
            throw new DuplicateResourceException("Role name already exists.");
        }

        Role role = roleMapper.toEntity(request);

        role = roleRepository.save(role);

        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse update(Long id, RoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found."));

        if (!role.getRoleCode().equals(request.getRoleCode())
                && roleRepository.existsByRoleCode(request.getRoleCode())) {

            throw new DuplicateResourceException("Role code already exists.");
        }

        if (!role.getRoleName().equals(request.getRoleName())
                && roleRepository.existsByRoleName(request.getRoleName())) {

            throw new DuplicateResourceException("Role name already exists.");
        }

        role.setRoleCode(request.getRoleCode());
        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());
        role.setActive(request.getActive());

        role = roleRepository.save(role);

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found."));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAll() {

        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found."));

        roleRepository.delete(role);
    }
}