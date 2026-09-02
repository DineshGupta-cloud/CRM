package com.CRM.service.Impl;

import com.CRM.dto.response.LeadActivityResponse;
import com.CRM.entity.Employee;
import com.CRM.entity.Lead;
import com.CRM.entity.LeadActivity;
import com.CRM.entity.LeadActivityType;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.LeadActivityMapper;
import com.CRM.repository.EmployeeRepository;
import com.CRM.repository.LeadActivityRepository;
import com.CRM.repository.LeadActivityTypeRepository;
import com.CRM.repository.LeadRepository;
import com.CRM.service.LeadActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadActivityServiceImpl implements LeadActivityService {

    private final LeadActivityRepository activityRepository;
    private final LeadRepository leadRepository;
    private final LeadActivityTypeRepository activityTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final LeadActivityMapper mapper;

    @Override
    public void logActivity(
            Long leadId,
            String activityCode,
            Long employeeId,
            String title,
            String description,
            String oldValue,
            String newValue,
            String referenceType,
            Long referenceId,
            boolean systemGenerated) {

        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));

        LeadActivityType activityType = activityTypeRepository
                .findByActivityCode(activityCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Activity type not found."));

        Employee employee = null;

        if (employeeId != null) {
            employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Employee not found."));
        }

        LeadActivity activity = LeadActivity.builder()
                .activityNumber(getNextActivityNumber())
                .lead(lead)
                .activityType(activityType)
                .performedBy(employee)
                .title(title)
                .description(description)
                .oldValue(oldValue)
                .newValue(newValue)
                .referenceType(referenceType)
                .referenceId(referenceId)
                .systemGenerated(systemGenerated)
                .active(true)
                .deleted(false)
                .build();

        activityRepository.save(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivityResponse> findByLead(Long leadId) {

        leadRepository.findById(leadId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));

        return activityRepository
                .findByLeadIdOrderByCreatedDateDesc(leadId)
                .stream()
                .filter(activity -> !Boolean.TRUE.equals(activity.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<LeadActivityResponse> findAll() {

        return activityRepository.findAll()
                .stream()
                .filter(activity -> !Boolean.TRUE.equals(activity.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        LeadActivity activity = activityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Activity not found."));

        activity.setActive(false);
        activity.setDeleted(true);

        activityRepository.save(activity);
    }

    @Override
    public String getNextActivityNumber() {

        long count = activityRepository.count() + 1;

        return String.format("ACT%06d", count);
    }

}

