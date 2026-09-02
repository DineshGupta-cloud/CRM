package com.CRM.service.Impl;

import com.CRM.dto.request.CustomerActivityRequest;
import com.CRM.dto.response.CustomerActivityResponse;
import com.CRM.entity.*;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.CustomerActivityMapper;
import com.CRM.repository.*;
import com.CRM.service.CustomerActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerActivityServiceImpl implements CustomerActivityService {

    private final CustomerActivityRepository activityRepository;
    private final CustomerRepository customerRepository;
    private final CustomerActivityTypeRepository activityTypeRepository;
    private final EmployeeRepository employeeRepository;

    private final CustomerActivityMapper mapper;

    @Override
    public CustomerActivityResponse save(CustomerActivityRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        CustomerActivityType activityType = activityTypeRepository
                .findByActivityCode(request.getActivityCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Activity type not found."));

        Employee employee = employeeRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        CustomerActivity activity = mapper.toEntity(
                request,
                customer,
                activityType,
                employee
        );

        return mapper.toResponse(
                activityRepository.save(activity)
        );

    }
    @Override
    public List<CustomerActivityResponse> getByCustomer(Long customerId) {

        return activityRepository
                .findByCustomerIdOrderByActivityDateDesc(customerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void logActivity(
            Long customerId,
            String activityCode,
            Long employeeId,
            String title,
            String description,
            String oldValue,
            String newValue,
            String entityType,
            Long entityId,
            Boolean visible) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        CustomerActivityType activityType = activityTypeRepository
                .findByActivityCode(activityCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Activity type not found."));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        CustomerActivity activity = CustomerActivity.builder()
                .customer(customer)
                .activityType(activityType)
                .performedBy(employee)
                .title(title)
                .description(description)
                .oldValue(oldValue)
                .newValue(newValue)
                .entityType(entityType)
                .entityId(entityId)
                .visible(visible)
                .build();

        activityRepository.save(activity);
    }
}