package com.CRM.service;

import com.CRM.dto.request.DesignationRequest;
import com.CRM.dto.response.DesignationResponse;

import java.util.List;

public interface DesignationService {

    DesignationResponse create(DesignationRequest request);

    DesignationResponse update(Long id, DesignationRequest request);

    DesignationResponse getById(Long id);

    List<DesignationResponse> getAll();

    void delete(Long id);
}