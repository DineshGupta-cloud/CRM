package com.CRM.mapper;

import com.CRM.dto.request.LeadFollowUpRequest;
import com.CRM.dto.response.LeadFollowUpResponse;
import com.CRM.entity.Employee;
import com.CRM.entity.Lead;
import com.CRM.entity.LeadFollowUp;
import org.springframework.stereotype.Component;

@Component
public class LeadFollowUpMapper {

    public LeadFollowUp toEntity(LeadFollowUpRequest request) {

        if (request == null) {
            return null;
        }

        return LeadFollowUp.builder()
                .followUpDate(request.getFollowUpDate())
                .followUpTime(request.getFollowUpTime())
                .reminderBeforeMinutes(request.getReminderBeforeMinutes())
                .subject(request.getSubject())
                .remarks(request.getRemarks())
                .outcome(request.getOutcome())
                .nextFollowUpDate(request.getNextFollowUpDate())
                .nextFollowUpTime(request.getNextFollowUpTime())
                .build();
    }

    public void updateEntity(
            LeadFollowUp entity,
            LeadFollowUpRequest request) {

        entity.setFollowUpDate(request.getFollowUpDate());
        entity.setFollowUpTime(request.getFollowUpTime());
        entity.setReminderBeforeMinutes(request.getReminderBeforeMinutes());
        entity.setSubject(request.getSubject());
        entity.setRemarks(request.getRemarks());
        entity.setOutcome(request.getOutcome());
        entity.setNextFollowUpDate(request.getNextFollowUpDate());
        entity.setNextFollowUpTime(request.getNextFollowUpTime());
    }

    public LeadFollowUpResponse toResponse(LeadFollowUp entity) {

        if (entity == null) {
            return null;
        }

        Lead lead = entity.getLead();

        Employee employee = entity.getAssignedEmployee();

        return LeadFollowUpResponse.builder()
                .id(entity.getId())
                .followUpCode(entity.getFollowUpCode())

                .leadCode(lead != null
                        ? lead.getLeadCode()
                        : null)

                .leadName(lead != null
                        ? lead.getFirstName() + " " + lead.getLastName()
                        : null)

                .followUpType(entity.getFollowUpType() != null
                        ? entity.getFollowUpType().getTypeName()
                        : null)

                .status(entity.getStatus() != null
                        ? entity.getStatus().getStatusName()
                        : null)

                .assignedEmployee(employee != null
                        ? employee.getFirstName() + " " + employee.getLastName()
                        : null)

                .followUpDate(entity.getFollowUpDate())
                .followUpTime(entity.getFollowUpTime())
                .reminderBeforeMinutes(entity.getReminderBeforeMinutes())
                .subject(entity.getSubject())
                .remarks(entity.getRemarks())
                .outcome(entity.getOutcome())
                .nextFollowUpDate(entity.getNextFollowUpDate())
                .nextFollowUpTime(entity.getNextFollowUpTime())
                .completed(entity.getCompleted())
                .completedDate(entity.getCompletedDate())
                .notificationSent(entity.getNotificationSent())
                .active(entity.getActive())
                .build();
    }
}