package com.CRM.mapper;

import com.CRM.dto.response.LeadActivityResponse;
import com.CRM.entity.Employee;
import com.CRM.entity.Lead;
import com.CRM.entity.LeadActivity;
import org.springframework.stereotype.Component;

@Component
public class LeadActivityMapper {

    public LeadActivityResponse toResponse(LeadActivity entity) {

        if (entity == null) {
            return null;
        }

        Lead lead = entity.getLead();
        Employee employee = entity.getPerformedBy();

        return LeadActivityResponse.builder()
                .id(entity.getId())
                .activityNumber(entity.getActivityNumber())

                .leadCode(
                        lead != null ? lead.getLeadCode() : null
                )

                .leadName(
                        lead != null
                                ? lead.getFirstName() + " " + lead.getLastName()
                                : null
                )

                .activityType(
                        entity.getActivityType() != null
                                ? entity.getActivityType().getActivityName()
                                : null
                )

                .performedBy(
                        employee != null
                                ? employee.getFirstName() + " " + employee.getLastName()
                                : "System"
                )

                .title(entity.getTitle())
                .description(entity.getDescription())
                .oldValue(entity.getOldValue())
                .newValue(entity.getNewValue())
                .referenceType(entity.getReferenceType())
                .referenceId(entity.getReferenceId())
                .systemGenerated(entity.getSystemGenerated())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}