package com.CRM.service.Impl;

import com.CRM.dto.request.OpportunityActivityRequest;
import com.CRM.dto.response.OpportunityActivityResponse;
import com.CRM.entity.OpportunityActivity;
import com.CRM.entity.OpportunityActivityType;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.OpportunityActivityMapper;
import com.CRM.repository.OpportunityActivityRepository;
import com.CRM.repository.OpportunityActivityTypeRepository;
import com.CRM.service.OpportunityActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OpportunityActivityServiceImpl implements OpportunityActivityService {
    @Override
    public void logActivity(Long opportunityId, String activityCode, Long employeeId, String title, String description, String oldValue, String newValue, String module, Long referenceId, Boolean systemGenerated) {

    }

    @Override
    public List<OpportunityActivity> getByOpportunityId(Long opportunityId) {
        return null;
    }
//
//    private final OpportunityActivityRepository activityRepository;
//    private final OpportunityActivityTypeRepository typeRepository;
//
//    @Override
//    public void logActivity(Long opportunityId,
//                            String activityCode,
//                            Long employeeId,
//                            String title,
//                            String description,
//                            String oldValue,
//                            String newValue,
//                            String module,
//                            Long referenceId,
//                            Boolean systemGenerated) {
//
//        OpportunityActivityType activityType = typeRepository
//                .findByTypeCode(activityCode)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Opportunity activity type not found."));
//
//        OpportunityActivity activity = OpportunityActivity.builder()
//                .opportunityId(opportunityId)
//                .activityCode(activityCode)
//                .activityType(activityType)
//                .employeeId(employeeId)
//                .title(title)
//                .description(description)
//                .oldValue(oldValue)
//                .newValue(newValue)
//                .module(module)
//                .referenceId(referenceId)
//                .success(systemGenerated)
//                .build();
//
//        activityRepository.save(activity);
//    }
//
//    @Override
//    public List<OpportunityActivity> getByOpportunityId(Long opportunityId) {
//
//        return activityRepository.findByOpportunityIdOrderByCreatedDateDesc(opportunityId);
//    }



}