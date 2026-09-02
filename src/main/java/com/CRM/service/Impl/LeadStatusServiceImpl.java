package com.CRM.service.impl;

import com.CRM.dto.request.LeadStatusRequest;
import com.CRM.dto.response.LeadStatusResponse;
import com.CRM.entity.LeadStatus;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.LeadStatusMapper;
import com.CRM.repository.LeadStatusRepository;
import com.CRM.service.LeadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadStatusServiceImpl implements LeadStatusService {

    private final LeadStatusRepository repository;
    private final LeadStatusMapper mapper;

    @Override
    public LeadStatusResponse save(LeadStatusRequest request) {

        if (repository.existsByStatusNameIgnoreCase(request.getStatusName())) {
            throw new DuplicateResourceException("Lead status already exists.");
        }

        LeadStatus entity = mapper.toEntity(request);
        entity.setStatusCode(getNextStatusCode());

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public LeadStatusResponse update(Long id,
                                     LeadStatusRequest request) {

        LeadStatus entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead status not found."));

        mapper.updateEntity(entity, request);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public LeadStatusResponse findById(Long id) {

        return mapper.toResponse(

                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Lead status not found."))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadStatusResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        LeadStatus entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead status not found."));

        entity.setActive(false);
        entity.setDeleted(true);

        repository.save(entity);
    }

    @Override
    public String getNextStatusCode() {

        long count = repository.count() + 1;

        return String.format("LST%03d", count);
    }
}