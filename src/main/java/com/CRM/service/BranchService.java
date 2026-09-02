package com.CRM.service;

import com.CRM.dto.request.BranchRequest;
import com.CRM.dto.response.BranchResponse;

import java.util.List;

public interface BranchService {

    BranchResponse create(BranchRequest request);

    BranchResponse update(Long id, BranchRequest request);

    BranchResponse getById(Long id);

    List<BranchResponse> getAll();

    void delete(Long id);
}