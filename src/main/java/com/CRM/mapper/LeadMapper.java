package com.CRM.mapper;

import com.CRM.dto.request.LeadRequest;
import com.CRM.dto.response.LeadResponse;
import com.CRM.entity.Lead;
import org.springframework.stereotype.Component;

@Component
public class LeadMapper {

    public Lead toEntity(LeadRequest request) {

        if (request == null) {
            return null;
        }

        return Lead.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .companyName(request.getCompanyName())
                .designation(request.getDesignation())
                .email(request.getEmail())
                .alternateEmail(request.getAlternateEmail())
                .mobile(request.getMobile())
                .alternateMobile(request.getAlternateMobile())
                .phone(request.getPhone())
                .website(request.getWebsite())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .expectedRevenue(request.getExpectedRevenue())
                .probability(request.getProbability())
                .nextFollowUpDate(request.getNextFollowUpDate())
                .remarks(request.getRemarks())
                .build();
    }

    public void updateEntity(Lead entity,
                             LeadRequest request) {

        entity.setFirstName(request.getFirstName());
        entity.setMiddleName(request.getMiddleName());
        entity.setLastName(request.getLastName());
        entity.setCompanyName(request.getCompanyName());
        entity.setDesignation(request.getDesignation());
        entity.setEmail(request.getEmail());
        entity.setAlternateEmail(request.getAlternateEmail());
        entity.setMobile(request.getMobile());
        entity.setAlternateMobile(request.getAlternateMobile());
        entity.setPhone(request.getPhone());
        entity.setWebsite(request.getWebsite());
        entity.setAddressLine1(request.getAddressLine1());
        entity.setAddressLine2(request.getAddressLine2());
        entity.setCity(request.getCity());
        entity.setState(request.getState());
        entity.setCountry(request.getCountry());
        entity.setPostalCode(request.getPostalCode());
        entity.setExpectedRevenue(request.getExpectedRevenue());
        entity.setProbability(request.getProbability());
        entity.setNextFollowUpDate(request.getNextFollowUpDate());
        entity.setRemarks(request.getRemarks());
    }

    public LeadResponse toResponse(Lead entity) {

        if (entity == null) {
            return null;
        }

        return LeadResponse.builder()
                .id(entity.getId())
                .leadCode(entity.getLeadCode())
                .fullName(buildFullName(entity))
                .companyName(entity.getCompanyName())
                .designation(entity.getDesignation())
                .email(entity.getEmail())
                .mobile(entity.getMobile())
                .city(entity.getCity())
                .state(entity.getState())

                .source(entity.getSource() != null
                        ? entity.getSource().getSourceName()
                        : null)

                .status(entity.getStatus() != null
                        ? entity.getStatus().getStatusName()
                        : null)

                .priority(entity.getPriority() != null
                        ? entity.getPriority().getPriorityName()
                        : null)

                .industry(entity.getIndustry() != null
                        ? entity.getIndustry().getIndustryName()
                        : null)

                .assignedEmployee(entity.getAssignedEmployee() != null
                        ? entity.getAssignedEmployee().getFirstName()
                        + " "
                        + entity.getAssignedEmployee().getLastName()
                        : null)

                .expectedRevenue(entity.getExpectedRevenue())
                .probability(entity.getProbability())
                .nextFollowUpDate(entity.getNextFollowUpDate())
                .active(entity.getActive())
                .build();
    }

    private String buildFullName(Lead lead) {

        StringBuilder name = new StringBuilder();

        if (lead.getFirstName() != null)
            name.append(lead.getFirstName()).append(" ");

        if (lead.getMiddleName() != null &&
                !lead.getMiddleName().isBlank())
            name.append(lead.getMiddleName()).append(" ");

        if (lead.getLastName() != null)
            name.append(lead.getLastName());

        return name.toString().trim();
    }
}