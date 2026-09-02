package com.CRM.service;

import com.CRM.dto.request.LeadFollowUpRequest;
import com.CRM.dto.response.LeadFollowUpResponse;

import java.time.LocalDate;
import java.util.List;

public interface LeadFollowUpService {

    LeadFollowUpResponse save(LeadFollowUpRequest request);

    LeadFollowUpResponse update(Long id,
                                LeadFollowUpRequest request);

    LeadFollowUpResponse findById(Long id);

    List<LeadFollowUpResponse> findAll();

    List<LeadFollowUpResponse> findByLead(Long leadId);

    List<LeadFollowUpResponse> getTodayFollowUps();

    List<LeadFollowUpResponse> getOverdueFollowUps();

    List<LeadFollowUpResponse> getEmployeeFollowUps(Long employeeId);

    LeadFollowUpResponse complete(Long id,
                                  String outcome);

    void delete(Long id);

    String getNextFollowUpCode();
}