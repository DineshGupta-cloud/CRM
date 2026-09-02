package com.CRM.service.impl;

import com.CRM.dto.request.OpportunityStageRequest;
import com.CRM.dto.response.OpportunityStageResponse;
import com.CRM.entity.OpportunityStage;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.OpportunityStageMapper;
import com.CRM.repository.OpportunityStageRepository;
import com.CRM.service.OpportunityStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityStageServiceImpl implements OpportunityStageService {

    private final OpportunityStageRepository repository;
    private final OpportunityStageMapper mapper;

    @Override
    public OpportunityStageResponse create(OpportunityStageRequest request) {

        validateDuplicate(request, null);

        OpportunityStage stage = mapper.toEntity(request);

        OpportunityStage saved = repository.save(stage);

        return mapper.toResponse(saved);
    }

    @Override
    public OpportunityStageResponse update(Long id, OpportunityStageRequest request) {

        OpportunityStage stage = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opportunity stage not found."));

        validateDuplicate(request, id);

        mapper.updateEntity(stage, request);

        return mapper.toResponse(repository.save(stage));
    }

    @Override
    public OpportunityStageResponse getById(Long id) {

        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Opportunity stage not found."))
        );
    }

    @Override
    public List<OpportunityStageResponse> getAll() {

        return repository.findByDeletedFalseOrderByDisplayOrderAsc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        OpportunityStage stage = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opportunity stage not found."));

        stage.setDeleted(true);
        stage.setActive(false);

        repository.save(stage);
    }

    private void validateDuplicate(OpportunityStageRequest request, Long id) {

        repository.findByStageNameIgnoreCase(request.getStageName())
                .filter(stage -> !stage.getId().equals(id))
                .ifPresent(stage -> {
                    throw new DuplicateResourceException(
                            "Opportunity stage already exists.");
                });
    }
}