package com.CRM.service.impl;

import com.CRM.dto.request.IndustryRequest;
import com.CRM.dto.response.IndustryResponse;
import com.CRM.entity.Industry;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.IndustryMapper;
import com.CRM.repository.IndustryRepository;
import com.CRM.service.IndustryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository repository;
    private final IndustryMapper mapper;

    @Override
    public IndustryResponse save(IndustryRequest request) {

        if (repository.existsByIndustryNameIgnoreCase(request.getIndustryName())) {
            throw new DuplicateResourceException("Industry already exists.");
        }

        Industry entity = mapper.toEntity(request);

        entity.setIndustryCode(getNextIndustryCode());

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public IndustryResponse update(Long id,
                                   IndustryRequest request) {

        Industry entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Industry not found."));

        mapper.updateEntity(entity, request);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public IndustryResponse findById(Long id) {

        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Industry not found."))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndustryResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Industry entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Industry not found."));

        entity.setActive(false);
        entity.setDeleted(true);

        repository.save(entity);
    }

    @Override
    public String getNextIndustryCode() {

        return String.format("IND%03d", repository.count() + 1);
    }
}