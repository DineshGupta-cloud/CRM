package com.CRM.service.impl;

import com.CRM.constants.ActivityCodes;
import com.CRM.dto.request.LeadNoteRequest;
import com.CRM.dto.response.LeadNoteResponse;
import com.CRM.entity.Employee;
import com.CRM.entity.Lead;
import com.CRM.entity.LeadNote;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.LeadNoteMapper;
import com.CRM.repository.EmployeeRepository;
import com.CRM.repository.LeadNoteRepository;
import com.CRM.repository.LeadRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.LeadActivityService;
import com.CRM.service.LeadNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadNoteServiceImpl implements LeadNoteService {

    private final LeadNoteRepository noteRepository;
    private final LeadRepository leadRepository;
    private final EmployeeRepository employeeRepository;
    private final LeadNoteMapper mapper;
    private final LeadActivityService activityService;
    private final SecurityUtils securityUtils;

    @Override
    public LeadNoteResponse save(LeadNoteRequest request) {

        Lead lead = leadRepository.findById(request.getLeadId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));
        System.out.println("Lead " + lead.getAssignedEmployee());
        Long employeeId = securityUtils.getCurrentEmployeeId();

        System.out.println("Current Employee ID = " + employeeId);

        Employee employee = employeeRepository.findById(
                        securityUtils.getCurrentUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        LeadNote note = mapper.toEntity(request);

        note.setNoteCode(getNextNoteCode());
        note.setLead(lead);
        note.setEmployee(employee);

        LeadNote savedNote = noteRepository.save(note);

        activityService.logActivity(
                lead.getId(),
                ActivityCodes.NOTE_ADDED,
                employee.getId(),
                "Lead Note Added",
                savedNote.getNote(),
                null,
                null,
                "LEAD_NOTE",
                savedNote.getId(),
                true
        );

        return mapper.toResponse(savedNote);
    }

    @Override
    public LeadNoteResponse update(Long id,
                                   LeadNoteRequest request) {

        LeadNote note = noteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead note not found."));

        mapper.updateEntity(note, request);

        LeadNote updatedNote = noteRepository.save(note);

        activityService.logActivity(
                updatedNote.getLead().getId(),
                ActivityCodes.NOTE_ADDED,
                securityUtils.getCurrentEmployeeId(),
                "Lead Note Updated",
                updatedNote.getNote(),
                null,
                null,
                "LEAD_NOTE",
                updatedNote.getId(),
                true
        );

        return mapper.toResponse(updatedNote);
    }

    @Override
    @Transactional(readOnly = true)
    public LeadNoteResponse findById(Long id) {

        LeadNote note = noteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead note not found."));

        return mapper.toResponse(note);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadNoteResponse> findByLead(Long leadId) {

        return noteRepository.findByLeadIdOrderByCreatedDateDesc(leadId)
                .stream()
                .filter(note -> !Boolean.TRUE.equals(note.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadNoteResponse> findAll() {

        return noteRepository.findAll()
                .stream()
                .filter(note -> !Boolean.TRUE.equals(note.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        LeadNote note = noteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead note not found."));

        note.setActive(false);
        note.setDeleted(true);

        noteRepository.save(note);

        activityService.logActivity(
                note.getLead().getId(),
                ActivityCodes.NOTE_ADDED,
                securityUtils.getCurrentEmployeeId(),
                "Lead Note Deleted",
                note.getNote(),
                null,
                null,
                "LEAD_NOTE",
                note.getId(),
                true
        );
    }

    @Override
    public String getNextNoteCode() {

        long count = noteRepository.countByActiveTrue() + 1;

        return String.format("LNT%05d", count);
    }

}