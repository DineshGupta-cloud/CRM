package com.CRM.service.Impl;

import com.CRM.dto.request.FollowUpTypeRequest;
import com.CRM.dto.response.FollowUpTypeResponse;
import com.CRM.entity.FollowUpType;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.FollowUpTypeMapper;
import com.CRM.repository.FollowUpTypeRepository;
import com.CRM.service.FollowUpTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowUpTypeServiceImpl implements FollowUpTypeService {

    private final FollowUpTypeRepository repository;
    private final FollowUpTypeMapper mapper;

    @Override
    public FollowUpTypeResponse save(FollowUpTypeRequest request) {

        if (repository.existsByTypeNameIgnoreCase(request.getTypeName())) {
            throw new DuplicateResourceException("Follow-up type already exists.");
        }

        FollowUpType entity = mapper.toEntity(request);
        entity.setTypeCode(getNextTypeCode());

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public FollowUpTypeResponse update(Long id,
                                       FollowUpTypeRequest request) {

        FollowUpType entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Follow-up type not found."));

        mapper.updateEntity(entity, request);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public FollowUpTypeResponse findById(Long id) {

        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Follow-up type not found."))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowUpTypeResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        FollowUpType entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Follow-up type not found."));

        entity.setActive(false);
        entity.setDeleted(true);

        repository.save(entity);
    }

    @Override
    public String getNextTypeCode() {

        return String.format("FUT%03d", repository.count() + 1);
    }

}