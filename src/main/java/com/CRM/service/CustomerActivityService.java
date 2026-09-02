package com.CRM.service;

import com.CRM.dto.request.CustomerActivityRequest;
import com.CRM.dto.response.CustomerActivityResponse;

import java.util.List;

public interface CustomerActivityService {

    CustomerActivityResponse save(CustomerActivityRequest request);

    List<CustomerActivityResponse> getByCustomer(Long customerId);

    void logActivity(
            Long customerId,
            String activityCode,
            Long employeeId,
            String title,
            String description,
            String oldValue,
            String newValue,
            String entityType,
            Long entityId,
            Boolean visible
    );
}