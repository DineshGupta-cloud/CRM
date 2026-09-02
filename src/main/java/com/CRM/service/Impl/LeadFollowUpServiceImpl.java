package com.CRM.service.Impl;

import com.CRM.constants.ActivityCodes;
import com.CRM.dto.request.LeadFollowUpRequest;
import com.CRM.dto.response.LeadFollowUpResponse;
import com.CRM.entity.Employee;
import com.CRM.entity.FollowUpStatus;
import com.CRM.entity.FollowUpType;
import com.CRM.entity.Lead;
import com.CRM.entity.LeadFollowUp;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.LeadFollowUpMapper;
import com.CRM.repository.EmployeeRepository;
import com.CRM.repository.FollowUpStatusRepository;
import com.CRM.repository.FollowUpTypeRepository;
import com.CRM.repository.LeadFollowUpRepository;
import com.CRM.repository.LeadRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.LeadActivityService;
import com.CRM.service.LeadFollowUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadFollowUpServiceImpl implements LeadFollowUpService {

    private final LeadFollowUpRepository followUpRepository;
    private final LeadRepository leadRepository;
    private final EmployeeRepository employeeRepository;
    private final FollowUpTypeRepository followUpTypeRepository;
    private final FollowUpStatusRepository followUpStatusRepository;
    private final LeadFollowUpMapper mapper;

    private final LeadActivityService activityService;

    private final SecurityUtils securityUtils;

    @Override
    public LeadFollowUpResponse save(LeadFollowUpRequest request) {

        LeadFollowUp followUp = mapper.toEntity(request);

        followUp.setFollowUpCode(getNextFollowUpCode());

        setRelations(followUp, request);

        LeadFollowUp saved = followUpRepository.save(followUp);

        activityService.logActivity(
                saved.getLead().getId(),
                ActivityCodes.FOLLOWUP_CREATED,
                securityUtils.getCurrentEmployeeId(),
                "Follow-up Created",
                "New follow-up scheduled on " + saved.getFollowUpDate(),
                null,
                null,
                "FOLLOW_UP",
                saved.getId(),
                true
        );

        return mapper.toResponse(saved);
    }

    @Override
    public LeadFollowUpResponse update(Long id,
                                       LeadFollowUpRequest request) {

        LeadFollowUp followUp = followUpRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Follow-up not found."));

        mapper.updateEntity(followUp, request);

        setRelations(followUp, request);

        LeadFollowUp updated = followUpRepository.save(followUp);
        activityService.logActivity(
                updated.getLead().getId(),
                ActivityCodes.FOLLOWUP_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Follow-up Updated",
                "Follow-up details updated.",
                null,
                null,
                "FOLLOW_UP",
                updated.getId(),
                true
        );

        return mapper.toResponse(updated);
    }

    private void setRelations(LeadFollowUp entity,
                              LeadFollowUpRequest request) {

        Lead lead = leadRepository.findById(request.getLeadId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));

        Employee employee = employeeRepository.findById(request.getAssignedEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        FollowUpType followUpType = followUpTypeRepository
                .findById(request.getFollowUpTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Follow-up type not found."));

        FollowUpStatus status = followUpStatusRepository
                .findById(request.getStatusId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Follow-up status not found."));

        entity.setLead(lead);
        entity.setAssignedEmployee(employee);
        entity.setFollowUpType(followUpType);
        entity.setStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public LeadFollowUpResponse findById(Long id) {

        LeadFollowUp followUp = followUpRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Follow-up not found."));

        return mapper.toResponse(followUp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadFollowUpResponse> findAll() {

        return followUpRepository.findAll()
                .stream()
                .filter(f -> !Boolean.TRUE.equals(f.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadFollowUpResponse> findByLead(Long leadId) {

        leadRepository.findById(leadId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));

        return followUpRepository.findByLeadId(leadId)
                .stream()
                .filter(f -> !Boolean.TRUE.equals(f.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadFollowUpResponse> getTodayFollowUps() {

        return followUpRepository.findByFollowUpDate(LocalDate.now())
                .stream()
                .filter(f -> !Boolean.TRUE.equals(f.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadFollowUpResponse> getOverdueFollowUps() {

        return followUpRepository
                .findByCompletedFalseAndFollowUpDateLessThan(LocalDate.now())
                .stream()
                .filter(f -> !Boolean.TRUE.equals(f.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadFollowUpResponse> getEmployeeFollowUps(Long employeeId) {

        employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        return followUpRepository
                .findByAssignedEmployeeIdAndCompletedFalse(employeeId)
                .stream()
                .filter(f -> !Boolean.TRUE.equals(f.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }


    @Override
    public LeadFollowUpResponse complete(Long id,
                                         String outcome) {

        LeadFollowUp followUp = followUpRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Follow-up not found."));

        followUp.setCompleted(true);
        followUp.setOutcome(outcome);
        followUp.setCompletedDate(LocalDateTime.now());

        followUp.setStatus(getCompletedStatus());

//        LeadFollowUp saved = followUpRepository.save(followUp);
//
//        autoCreateNextFollowUp(saved);
//
//        return mapper.toResponse(saved);
        LeadFollowUp saved = followUpRepository.save(followUp);

        activityService.logActivity(
                saved.getLead().getId(),
                ActivityCodes.FOLLOWUP_COMPLETED,
                securityUtils.getCurrentEmployeeId(),
                "Follow-up Completed",
                saved.getOutcome(),
                null,
                null,
                "FOLLOW_UP",
                saved.getId(),
                true
        );

        autoCreateNextFollowUp(saved);

        return mapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {

        LeadFollowUp followUp = followUpRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Follow-up not found."));

        followUp.setActive(false);
        followUp.setDeleted(true);

        followUpRepository.save(followUp);
    }

    @Override
    public String getNextFollowUpCode() {

        long count = followUpRepository.count() + 1;

        return String.format("FUP%05d", count);
    }

    /**
     * COMPLETED
     */
    private FollowUpStatus getCompletedStatus() {

        return followUpStatusRepository
                .findByStatusCode("FUS002")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Completed status not found."));
    }

    /**
     * SCHEDULED
     */
    private FollowUpStatus getScheduledStatus() {

        return followUpStatusRepository
                .findByStatusCode("FUS001")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Scheduled status not found."));
    }

    /**
     * Automatically creates the next follow-up if a next date is provided.
     */private void autoCreateNextFollowUp(LeadFollowUp completedFollowUp) {

        if (completedFollowUp.getNextFollowUpDate() == null) {
            return;
        }

        LeadFollowUp next = LeadFollowUp.builder()
                .followUpCode(getNextFollowUpCode())
                .lead(completedFollowUp.getLead())
                .assignedEmployee(completedFollowUp.getAssignedEmployee())
                .followUpType(completedFollowUp.getFollowUpType())
                .status(getScheduledStatus())
                .followUpDate(completedFollowUp.getNextFollowUpDate())
                .followUpTime(completedFollowUp.getNextFollowUpTime())
                .subject(completedFollowUp.getSubject())
                .remarks(completedFollowUp.getRemarks())
                .reminderBeforeMinutes(completedFollowUp.getReminderBeforeMinutes())
                .completed(false)
                .notificationSent(false)
                .active(true)
                .deleted(false)
                .build();

        LeadFollowUp nextFollowUp = followUpRepository.save(next);

        activityService.logActivity(
                nextFollowUp.getLead().getId(),
                ActivityCodes.FOLLOWUP_CREATED,
                securityUtils.getCurrentEmployeeId(),
                "Next Follow-up Scheduled",
                "Automatically scheduled next follow-up on "
                        + nextFollowUp.getFollowUpDate(),
                null,
                null,
                "FOLLOW_UP",
                nextFollowUp.getId(),
                true
        );
    }
}