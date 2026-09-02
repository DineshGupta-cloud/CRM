package com.CRM.service.Impl;

import com.CRM.dto.request.LeadSourceRequest;
import com.CRM.dto.response.LeadSourceResponse;
import com.CRM.entity.LeadSource;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.LeadSourceMapper;
import com.CRM.repository.LeadSourceRepository;
import com.CRM.service.LeadSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadSourceServiceImpl implements LeadSourceService {

    private final LeadSourceRepository repository;
    private final LeadSourceMapper mapper;

    @Override
    public LeadSourceResponse save(LeadSourceRequest request) {

        if (repository.existsBySourceNameIgnoreCase(request.getSourceName())) {
            throw new DuplicateResourceException("Lead source already exists.");
        }

        LeadSource entity = mapper.toEntity(request);
        entity.setSourceCode(getNextSourceCode());

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public LeadSourceResponse update(Long id, LeadSourceRequest request) {

        LeadSource entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead source not found."));

        mapper.updateEntity(entity, request);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public LeadSourceResponse findById(Long id) {

        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Lead source not found."))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadSourceResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        LeadSource entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead source not found."));

        entity.setActive(false);
        entity.setDeleted(true);

        repository.save(entity);
    }

    @Override
    public String getNextSourceCode() {

        long count = repository.count() + 1;

        return String.format("LS%03d", count);
    }
}