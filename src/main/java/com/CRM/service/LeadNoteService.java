package com.CRM.service;

import com.CRM.dto.request.LeadNoteRequest;
import com.CRM.dto.response.LeadNoteResponse;

import java.util.List;

public interface LeadNoteService {

    LeadNoteResponse save(LeadNoteRequest request);

    LeadNoteResponse update(Long id,
                            LeadNoteRequest request);

    LeadNoteResponse findById(Long id);

    List<LeadNoteResponse> findAll();

    List<LeadNoteResponse> findByLead(Long leadId);

    void delete(Long id);

    String getNextNoteCode();

}