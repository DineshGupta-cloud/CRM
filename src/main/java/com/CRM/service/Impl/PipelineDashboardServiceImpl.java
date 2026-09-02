package com.CRM.service.impl;

import com.CRM.dto.response.PipelineStageSummaryResponse;
import com.CRM.entity.Opportunity;
import com.CRM.entity.OpportunityStage;
import com.CRM.repository.OpportunityRepository;
import com.CRM.repository.OpportunityStageRepository;
import com.CRM.service.PipelineDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PipelineDashboardServiceImpl implements PipelineDashboardService {

    private final OpportunityStageRepository stageRepository;
    private final OpportunityRepository opportunityRepository;

    @Override
    public List<PipelineStageSummaryResponse> getPipelineSummary() {

        List<OpportunityStage> stages =
                stageRepository.findByDeletedFalseOrderByDisplayOrderAsc();

        List<Opportunity> opportunities =
                opportunityRepository.findByDeletedFalseOrderByCreatedDateDesc();

        return stages.stream().map(stage -> {

            List<Opportunity> stageOpps = opportunities.stream()
                    .filter(o -> o.getStage().getId().equals(stage.getId()))
                    .toList();

            BigDecimal totalRevenue = stageOpps.stream()
                    .map(o -> o.getExpectedRevenue() == null
                            ? BigDecimal.ZERO
                            : o.getExpectedRevenue())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal weightedRevenue = stageOpps.stream()
                    .map(o -> {
                        BigDecimal rev = o.getExpectedRevenue() == null
                                ? BigDecimal.ZERO
                                : o.getExpectedRevenue();

                        BigDecimal prob = o.getProbability() == null
                                ? BigDecimal.ZERO
                                : o.getProbability().divide(BigDecimal.valueOf(100));

                        return rev.multiply(prob);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return PipelineStageSummaryResponse.builder()
                    .stageId(stage.getId())
                    .stageName(stage.getStageName())
                    .totalOpportunities((long) stageOpps.size())
                    .totalRevenue(totalRevenue)
                    .weightedRevenue(weightedRevenue)
                    .build();

        }).toList();
    }
}