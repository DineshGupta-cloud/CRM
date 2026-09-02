package com.CRM.service.impl;

import com.CRM.dto.request.LeadPriorityRequest;
import com.CRM.dto.response.LeadPriorityResponse;
import com.CRM.entity.LeadPriority;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.LeadPriorityMapper;
import com.CRM.repository.LeadPriorityRepository;
import com.CRM.service.LeadPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadPriorityServiceImpl implements LeadPriorityService {

    private final LeadPriorityRepository repository;
    private final LeadPriorityMapper mapper;

    @Override
    public LeadPriorityResponse save(LeadPriorityRequest request) {

        if (repository.existsByPriorityNameIgnoreCase(request.getPriorityName())) {
            throw new DuplicateResourceException("Lead priority already exists.");
        }

        LeadPriority entity = mapper.toEntity(request);
        entity.setPriorityCode(getNextPriorityCode());

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public LeadPriorityResponse update(Long id,
                                       LeadPriorityRequest request) {

        LeadPriority entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead priority not found."));

        mapper.updateEntity(entity, request);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public LeadPriorityResponse findById(Long id) {

        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Lead priority not found."))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadPriorityResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        LeadPriority entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead priority not found."));

        entity.setActive(false);
        entity.setDeleted(true);

        repository.save(entity);
    }

    @Override
    public String getNextPriorityCode() {

        return String.format("LPR%03d", repository.count() + 1);
    }
}