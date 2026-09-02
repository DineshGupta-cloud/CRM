package com.CRM.service.Impl;

import com.CRM.constants.OpportunityActivityCodes;
import com.CRM.dto.request.OpportunityRequest;
import com.CRM.dto.response.OpportunityResponse;
import com.CRM.entity.*;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.OpportunityMapper;
import com.CRM.repository.CustomerRepository;
import com.CRM.repository.EmployeeRepository;
import com.CRM.repository.OpportunityRepository;
import com.CRM.repository.OpportunityStageRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.OpportunityActivityService;
import com.CRM.service.OpportunityAggregationService;
import com.CRM.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {

        private final OpportunityActivityService opportunityActivityService;
        private final OpportunityRepository opportunityRepository;
        private final CustomerRepository customerRepository;
        private final EmployeeRepository employeeRepository;
        private final OpportunityStageRepository stageRepository;

        private final OpportunityMapper mapper;

        private final SecurityUtils securityUtils;

//         private final OpportunityActivity activity;




    @Override
    public void logActivity(Long opportunityId, String activityCode, Long employeeId, String title, String description, String oldValue, String newValue, String module, Long referenceId, Boolean success) {

    }

        @Override
    public OpportunityResponse create(OpportunityRequest request) {

        validateDuplicate(request);

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        OpportunityStage stage = stageRepository.findById(request.getStageId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opportunity stage not found."));

        Employee employee = null;

        if (request.getAssignedEmployeeId() != null) {
            employee = employeeRepository.findById(request.getAssignedEmployeeId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Employee not found."));
        }

        Opportunity opportunity =
                mapper.toEntity(request, customer, stage, employee);

        opportunity.setOpportunityCode(getNextOpportunityCode());

        Opportunity saved = opportunityRepository.save(opportunity);

        opportunityActivityService.logActivity(
                saved.getId(),
                OpportunityActivityCodes.OPPORTUNITY_CREATED,
                securityUtils.getCurrentEmployeeId(),
                "Opportunity Created",
                "Opportunity created successfully.",
                null,
                null,
                "OPPORTUNITY",
                saved.getId(),
                true
        );

        return mapper.toResponse(saved);
    }

    @Override
    public OpportunityResponse update(Long id, OpportunityRequest request) {

        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opportunity not found."));
        validateDuplicate(request, id);
        String oldStage = opportunity.getStage().getStageName();

        String oldEmployee = opportunity.getAssignedEmployee() != null
                ? opportunity.getAssignedEmployee().getFirstName() + " "
                + opportunity.getAssignedEmployee().getLastName()
                : null;

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        OpportunityStage stage = stageRepository.findById(request.getStageId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opportunity stage not found."));

        Employee employee = null;
                if (request.getAssignedEmployeeId() != null) {
            employee = employeeRepository.findById(request.getAssignedEmployeeId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Employee not found."));
        }

        mapper.updateEntity(opportunity, request, stage, employee);
        opportunity.setCustomer(customer);

        Opportunity updated = opportunityRepository.save(opportunity);
        opportunityActivityService.logActivity(
                updated.getId(),
                OpportunityActivityCodes.OPPORTUNITY_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Opportunity Updated",
                "Opportunity updated successfully.",
                null,
                null,
                "OPPORTUNITY",
                updated.getId(),
                true
        );

        String newStage = updated.getStage().getStageName();

        if (!java.util.Objects.equals(oldStage, newStage)) {

            opportunityActivityService.logActivity(
                    updated.getId(),
                    OpportunityActivityCodes.STAGE_CHANGED,
                    securityUtils.getCurrentEmployeeId(),
                    "Opportunity Stage Changed",
                    "Opportunity stage changed.",
                    oldStage,
                    newStage,
                    "OPPORTUNITY",
                    updated.getId(),
                    true
            );
        }

        String newEmployee = updated.getAssignedEmployee() != null
                ? updated.getAssignedEmployee().getFirstName() + " "
                + updated.getAssignedEmployee().getLastName()
                : null;

        if (!java.util.Objects.equals(oldEmployee, newEmployee)) {

            opportunityActivityService.logActivity(
                    updated.getId(),
                    OpportunityActivityCodes.OPPORTUNITY_ASSIGNED,
                    securityUtils.getCurrentEmployeeId(),
                    "Opportunity Reassigned",
                    "Opportunity assigned to another employee.",
                    oldEmployee,
                    newEmployee,
                    "OPPORTUNITY",
                    updated.getId(),
                    true
            );
        }

        return mapper.toResponse(updated);
    }


    @Override
    public OpportunityResponse getById(Long id) {

        return mapper.toResponse(
                opportunityRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Opportunity not found."))
        );
    }

    @Override
    public List<OpportunityResponse> getAll() {

        return opportunityRepository.findByDeletedFalseOrderByCreatedDateDesc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<OpportunityResponse> getByCustomer(Long customerId) {

        return opportunityRepository
                .findByCustomerIdAndDeletedFalseOrderByCreatedDateDesc(customerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opportunity not found."));

        opportunity.setDeleted(true);
        opportunity.setActive(false);

        opportunityRepository.save(opportunity);

        opportunityActivityService.logActivity(
                opportunity.getId(),
                OpportunityActivityCodes.OPPORTUNITY_DELETED,
                securityUtils.getCurrentEmployeeId(),
                "Opportunity Deleted",
                "Opportunity deleted.",
                null,
                null,
                "OPPORTUNITY",
                opportunity.getId(),
                true
        );
    }



    private void validateDuplicate(OpportunityRequest request) {
        validateDuplicate(request, null);
    }

    private void validateDuplicate(OpportunityRequest request, Long opportunityId) {

        opportunityRepository.findByOpportunityNameIgnoreCase(
                        request.getOpportunityName())
                .filter(opportunity -> !opportunity.getId().equals(opportunityId))
                .ifPresent(opportunity -> {
                    throw new DuplicateResourceException(
                            "Opportunity already exists.");
                });
    }

    private String getNextOpportunityCode() {

        return opportunityRepository.findTopByOrderByIdDesc()
                .map(opportunity -> {
                    String code = opportunity.getOpportunityCode().replace("OPP", "");
                    int next = Integer.parseInt(code) + 1;
                    return String.format("OPP%05d", next);
                })
                .orElse("OPP00001");
    }
//
//    @Override
//    public void logActivity(
//            Long opportunityId,
//            String activityCode,
//            Long employeeId,
//            String title,
//            String description,
//            String oldValue,
//            String newValue,
//            String module,
//            Long referenceId,
//            Boolean success) {
//
//        OpportunityActivity activity = OpportunityActivity.builder()
//                .opportunityId(opportunityId)
//                .activityCode(activityCode)
//                .employeeId(employeeId)
//                .title(title)
//                .description(description)
//                .oldValue(oldValue)
//                .newValue(newValue)
//                .module(module)
//                .referenceId(referenceId)
//                .success(success)
//                .build();
//
////        activityRepository.save(activity);
//    }


}