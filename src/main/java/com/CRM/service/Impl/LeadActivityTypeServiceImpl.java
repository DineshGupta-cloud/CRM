package com.CRM.service.Impl;

import com.CRM.dto.request.LeadActivityTypeRequest;
import com.CRM.dto.response.LeadActivityTypeResponse;
import com.CRM.entity.LeadActivityType;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.LeadActivityTypeMapper;
import com.CRM.repository.LeadActivityTypeRepository;
import com.CRM.service.LeadActivityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadActivityTypeServiceImpl implements LeadActivityTypeService {

    private final LeadActivityTypeRepository repository;
    private final LeadActivityTypeMapper mapper;

    @Override
    public LeadActivityTypeResponse save(LeadActivityTypeRequest request) {

        if (repository.existsByActivityNameIgnoreCase(request.getActivityName())) {
            throw new DuplicateResourceException("Activity type already exists.");
        }

        LeadActivityType entity = mapper.toEntity(request);

        entity.setActivityCode(getNextActivityCode());

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public LeadActivityTypeResponse update(Long id,
                                           LeadActivityTypeRequest request) {

        LeadActivityType entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Activity type not found."));

        mapper.updateEntity(entity, request);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public LeadActivityTypeResponse findById(Long id) {

        LeadActivityType entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Activity type not found."));

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivityTypeResponse> findAll() {

        return repository.findAll()
                .stream()
                .filter(type -> !Boolean.TRUE.equals(type.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        LeadActivityType entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Activity type not found."));

        entity.setActive(false);
        entity.setDeleted(true);

        repository.save(entity);
    }

    @Override
    public String getNextActivityCode() {

        long count = repository.count() + 1;

        return String.format("LAT%03d", count);
    }
}