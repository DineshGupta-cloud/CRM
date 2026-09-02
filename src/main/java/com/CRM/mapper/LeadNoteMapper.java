package com.CRM.mapper;

import com.CRM.dto.request.LeadNoteRequest;
import com.CRM.dto.response.LeadNoteResponse;
import com.CRM.entity.Employee;
import com.CRM.entity.Lead;
import com.CRM.entity.LeadNote;
import org.springframework.stereotype.Component;

@Component
public class LeadNoteMapper {

    public LeadNote toEntity(LeadNoteRequest request) {

        if (request == null) {
            return null;
        }

        return LeadNote.builder()
                .note(request.getNote())
                .pinned(Boolean.TRUE.equals(request.getPinned()))
                .privateNote(Boolean.TRUE.equals(request.getPrivateNote()))
                .build();
    }

    public void updateEntity(LeadNote entity,
                             LeadNoteRequest request) {

        entity.setNote(request.getNote());
        entity.setPinned(Boolean.TRUE.equals(request.getPinned()));
        entity.setPrivateNote(Boolean.TRUE.equals(request.getPrivateNote()));
    }

    public LeadNoteResponse toResponse(LeadNote entity) {

        Lead lead = entity.getLead();
        Employee employee = entity.getEmployee();

        return LeadNoteResponse.builder()
                .id(entity.getId())
                .noteCode(entity.getNoteCode())

                .leadCode(
                        lead != null ? lead.getLeadCode() : null
                )

                .employeeName(
                        employee != null
                                ? employee.getFirstName() + " " + employee.getLastName()
                                : null
                )

                .note(entity.getNote())
                .pinned(entity.getPinned())
                .privateNote(entity.getPrivateNote())
                .createdDate(entity.getCreatedDate())
                .build();
    }

}