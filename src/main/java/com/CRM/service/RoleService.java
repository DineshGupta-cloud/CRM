package com.CRM.service;

import com.CRM.dto.request.RoleRequest;
import com.CRM.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    RoleResponse update(Long id, RoleRequest request);

    RoleResponse getById(Long id);

    List<RoleResponse> getAll();

    void delete(Long id);

}