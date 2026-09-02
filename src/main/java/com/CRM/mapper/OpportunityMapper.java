package com.CRM.mapper;

import com.CRM.dto.request.OpportunityRequest;
import com.CRM.dto.response.OpportunityResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.Employee;
import com.CRM.entity.Opportunity;
import com.CRM.entity.OpportunityStage;
import org.springframework.stereotype.Component;

@Component
public class OpportunityMapper {

    public Opportunity toEntity(OpportunityRequest request,
                                Customer customer,
                                OpportunityStage stage,
                                Employee employee) {

        return Opportunity.builder()
                .opportunityName(request.getOpportunityName())
                .customer(customer)
                .stage(stage)
                .assignedEmployee(employee)
                .expectedRevenue(request.getExpectedRevenue())
                .probability(request.getProbability())
                .expectedCloseDate(request.getExpectedCloseDate())
                .description(request.getDescription())
                .active(true)
                .deleted(false)
                .build();
    }

    public void updateEntity(Opportunity opportunity,
                             OpportunityRequest request,
                             OpportunityStage stage,
                             Employee employee) {

        opportunity.setOpportunityName(request.getOpportunityName());
        opportunity.setStage(stage);
        opportunity.setAssignedEmployee(employee);
        opportunity.setExpectedRevenue(request.getExpectedRevenue());
        opportunity.setProbability(request.getProbability());
        opportunity.setExpectedCloseDate(request.getExpectedCloseDate());
        opportunity.setDescription(request.getDescription());
    }

    public OpportunityResponse toResponse(Opportunity opportunity) {

        return OpportunityResponse.builder()
                .id(opportunity.getId())
                .opportunityCode(opportunity.getOpportunityCode())
                .opportunityName(opportunity.getOpportunityName())
                .customerCode(opportunity.getCustomer().getCustomerCode())
                .customerName(opportunity.getCustomer().getCustomerName())
                .stage(opportunity.getStage().getStageName())
                .assignedEmployee(
                        opportunity.getAssignedEmployee() == null
                                ? null
                                : opportunity.getAssignedEmployee().getFirstName()
                                + " "
                                + opportunity.getAssignedEmployee().getLastName()
                )
                .expectedRevenue(opportunity.getExpectedRevenue())
                .probability(opportunity.getProbability())
                .expectedCloseDate(opportunity.getExpectedCloseDate())
                .description(opportunity.getDescription())
                .active(opportunity.getActive())
                .build();
    }
}