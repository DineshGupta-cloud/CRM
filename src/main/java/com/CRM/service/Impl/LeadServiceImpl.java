package com.CRM.service.Impl;

import com.CRM.constants.ActivityCodes;
import com.CRM.dto.request.LeadRequest;
import com.CRM.dto.response.LeadResponse;
import com.CRM.entity.*;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.LeadMapper;
import com.CRM.repository.*;
import com.CRM.security.SecurityUtils;
import com.CRM.service.LeadActivityService;
import com.CRM.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    private final LeadSourceRepository leadSourceRepository;
    private final LeadStatusRepository leadStatusRepository;
    private final LeadPriorityRepository leadPriorityRepository;
    private final IndustryRepository industryRepository;
    private final EmployeeRepository employeeRepository;
    private final LeadMapper mapper;

    private final SecurityUtils securityUtils;
    private final LeadActivityService activityService;

    @Override
    public LeadResponse save(LeadRequest request) {

        validateDuplicate(request,null);

        Lead lead = mapper.toEntity(request);

        lead.setLeadCode(getNextLeadCode());

        setRelations(lead, request);

        Lead savedLead = leadRepository.save(lead);

        activityService.logActivity(
                savedLead.getId(),
                "LAT001",
                securityUtils.getCurrentEmployeeId(), // we'll implement this next getCurrentEmployeeId()
                "Lead Created",
                "Lead " + savedLead.getLeadCode() + " created successfully.",
                null,
                null,
                "LEAD",
                savedLead.getId(),
                true
        );

        return mapper.toResponse(savedLead);
    }


    @Override
    public LeadResponse update(Long id, LeadRequest request) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));

        validateDuplicate(request,id);

        // Store old values BEFORE updating


        String oldStatus = lead.getStatus() != null
                ? lead.getStatus().getStatusName()
                : null;

        Long oldEmployeeId = lead.getAssignedEmployee() != null
                ? lead.getAssignedEmployee().getId()
                : null;

        String oldEmployee = lead.getAssignedEmployee() != null
                ? lead.getAssignedEmployee().getFirstName() + " " +
                lead.getAssignedEmployee().getLastName()
                : null;

        mapper.updateEntity(lead, request);

        setRelations(lead, request);

        Lead updatedLead = leadRepository.save(lead);

        // General update activity
        activityService.logActivity(
                updatedLead.getId(),
                ActivityCodes.LEAD_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Lead Updated",
                "Lead details updated successfully.",
                null,
                null,
                "LEAD",
                updatedLead.getId(),
                true
        );

        // Status changed?
        String newStatus = updatedLead.getStatus() != null
                ? updatedLead.getStatus().getStatusName()
                : null;

        if (!java.util.Objects.equals(oldStatus, newStatus)) {

            activityService.logActivity(
                    updatedLead.getId(),
                    ActivityCodes.STATUS_CHANGED,
                    securityUtils.getCurrentEmployeeId(),
                    "Lead Status Changed",
                    "Lead status changed.",
                    oldStatus,
                    newStatus,
                    "LEAD",
                    updatedLead.getId(),
                    true
            );
        }

        // Assignment changed?
        Long newEmployeeId = updatedLead.getAssignedEmployee() != null
                ? updatedLead.getAssignedEmployee().getId()
                : null;

        if (!java.util.Objects.equals(oldEmployeeId, newEmployeeId)) {

            String newEmployee = updatedLead.getAssignedEmployee() != null
                    ? updatedLead.getAssignedEmployee().getFirstName() + " " +
                    updatedLead.getAssignedEmployee().getLastName()
                    : null;

            activityService.logActivity(
                    updatedLead.getId(),
                    ActivityCodes.LEAD_ASSIGNED,
                    securityUtils.getCurrentEmployeeId(),
                    "Lead Reassigned",
                    "Lead assigned to another employee.",
                    oldEmployee,
                    newEmployee,
                    "LEAD",
                    updatedLead.getId(),
                    true
            );
        }

        return mapper.toResponse(updatedLead);
    }


    @Override
    @Transactional(readOnly = true)
    public LeadResponse findById(Long id) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));

        return mapper.toResponse(lead);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadResponse> findAll() {

        return leadRepository.findAll()
                .stream()
                .filter(lead -> !Boolean.TRUE.equals(lead.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));

        lead.setActive(false);
        lead.setDeleted(true);

        leadRepository.save(lead);
    }

    @Override
    public String getNextLeadCode() {

        long count = leadRepository.count() + 1;

        return String.format("LEAD%05d", count);
    }
    /**
     * Validate duplicate email/mobile while creating
//     */
//    private void validateDuplicate(LeadRequest request,Long id) {
//
//        if (request.getEmail() != null &&
//                leadRepository.existsByEmail(request.getEmail())) {
//
//            throw new DuplicateResourceException(
//                    "Lead already exists with email.");
//        }
//
//        if (request.getMobile() != null &&
//                leadRepository.existsByMobile(request.getMobile())) {
//
//            throw new DuplicateResourceException(
//                    "Lead already exists with mobile.");
//        }
//    }

    private void validateDuplicate(LeadRequest request, Long leadId) {

        // Email
        if (request.getEmail() != null && !request.getEmail().isBlank()) {

            leadRepository.findByEmail(request.getEmail())
                    .ifPresent(existingLead -> {

                        if (leadId == null || !existingLead.getId().equals(leadId)) {
                            throw new DuplicateResourceException(
                                    "Lead already exists with email."
                            );
                        }
                    });
        }

        // Mobile
        if (request.getMobile() != null && !request.getMobile().isBlank()) {

            leadRepository.findByMobile(request.getMobile())
                    .ifPresent(existingLead -> {

                        if (leadId == null || !existingLead.getId().equals(leadId)) {
                            throw new DuplicateResourceException(
                                    "Lead already exists with mobile."
                            );
                        }
                    });
        }
    }
    /**
     * Validate duplicate email/mobile while updating
     */
    private void validateDuplicateForUpdate(Long id,
                                            LeadRequest request) {

        leadRepository.findAll()
                .stream()
                .filter(l -> !l.getId().equals(id))
                .forEach(existing -> {

                    if (request.getEmail() != null &&
                            request.getEmail().equalsIgnoreCase(existing.getEmail())) {

                        throw new DuplicateResourceException(
                                "Lead email already exists.");
                    }

                    if (request.getMobile() != null &&
                            request.getMobile().equals(existing.getMobile())) {

                        throw new DuplicateResourceException(
                                "Lead mobile already exists.");
                    }

                });
    }

    /**
     * Load all lookup entities
     */
    private void setRelations(Lead lead,
                              LeadRequest request) {

        LeadSource source = leadSourceRepository.findById(request.getSourceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead Source not found."));

        LeadStatus status = leadStatusRepository.findById(request.getStatusId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead Status not found."));

        LeadPriority priority = leadPriorityRepository.findById(request.getPriorityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead Priority not found."));

        Industry industry = industryRepository.findById(request.getIndustryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Industry not found."));

        Employee employee = employeeRepository.findById(request.getAssignedEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        lead.setSource(source);
        lead.setStatus(status);
        lead.setPriority(priority);
        lead.setIndustry(industry);
        lead.setAssignedEmployee(employee);
    }
}