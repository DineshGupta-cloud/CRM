package com.CRM.mapper;

import com.CRM.dto.request.OpportunityStageRequest;
import com.CRM.dto.response.OpportunityStageResponse;
import com.CRM.entity.OpportunityStage;
import org.springframework.stereotype.Component;

@Component
public class OpportunityStageMapper {

    public OpportunityStage toEntity(OpportunityStageRequest request) {

        return OpportunityStage.builder()
                .stageCode(request.getStageCode())
                .stageName(request.getStageName())
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder())
                .isClosed(request.getIsClosed() == null ? false : request.getIsClosed())
                .isWon(request.getIsWon() == null ? false : request.getIsWon())
                .isLost(request.getIsLost() == null ? false : request.getIsLost())
                .active(request.getActive() == null ? true : request.getActive())
                .deleted(false)
                .build();
    }

    public void updateEntity(OpportunityStage stage,
                             OpportunityStageRequest request) {

        stage.setStageCode(request.getStageCode());
        stage.setStageName(request.getStageName());
        stage.setDescription(request.getDescription());
        stage.setDisplayOrder(request.getDisplayOrder());

        if (request.getIsClosed() != null) {
            stage.setIsClosed(request.getIsClosed());
        }

        if (request.getIsWon() != null) {
            stage.setIsWon(request.getIsWon());
        }

        if (request.getIsLost() != null) {
            stage.setIsLost(request.getIsLost());
        }

        if (request.getActive() != null) {
            stage.setActive(request.getActive());
        }
    }

    public OpportunityStageResponse toResponse(OpportunityStage stage) {

        return OpportunityStageResponse.builder()
                .id(stage.getId())
                .stageCode(stage.getStageCode())
                .stageName(stage.getStageName())
                .description(stage.getDescription())
                .displayOrder(stage.getDisplayOrder())
                .isClosed(stage.getIsClosed())
                .isWon(stage.getIsWon())
                .isLost(stage.getIsLost())
                .active(stage.getActive())
                .build();
    }
}