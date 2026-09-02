package com.CRM.service.Impl;

import com.CRM.dto.request.DesignationRequest;
import com.CRM.dto.response.DesignationResponse;
import com.CRM.entity.Designation;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.DesignationMapper;
import com.CRM.repository.DesignationRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.DesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;
    private final DesignationMapper designationMapper;
    private final SecurityUtils securityUtils;

    @Override
    public DesignationResponse create(DesignationRequest request) {

        if (designationRepository.existsByDesignationCode(request.getDesignationCode())) {
            throw new DuplicateResourceException("Designation code already exists.");
        }

        if (designationRepository.existsByDesignationName(request.getDesignationName())) {
            throw new DuplicateResourceException("Designation name already exists.");
        }

        Designation designation = designationMapper.toEntity(request);

        designation = designationRepository.save(designation);

        return designationMapper.toResponse(designation);
    }

    @Override
    public DesignationResponse update(Long id, DesignationRequest request) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Designation not found."));

        if (!designation.getDesignationCode().equals(request.getDesignationCode())
                && designationRepository.existsByDesignationCode(request.getDesignationCode())) {

            throw new DuplicateResourceException("Designation code already exists.");
        }

        if (!designation.getDesignationName().equals(request.getDesignationName())
                && designationRepository.existsByDesignationName(request.getDesignationName())) {

            throw new DuplicateResourceException("Designation name already exists.");
        }

        designation.setDesignationCode(request.getDesignationCode());
        designation.setDesignationName(request.getDesignationName());
        designation.setActive(request.getActive());

        designation = designationRepository.save(designation);

        return designationMapper.toResponse(designation);
    }

    @Override
    @Transactional(readOnly = true)
    public DesignationResponse getById(Long id) {

        Designation designation = designationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Designation not found."));

        return designationMapper.toResponse(designation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignationResponse> getAll() {

        return designationRepository.findByDeletedFalse()
                .stream()
                .map(designationMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        designation.setActive(false);
        designation.setDeleted(true);

        designation.setDeletedBy(securityUtils.getCurrentUserId());
        designation.setDeletedDate(LocalDateTime.now());

        designationRepository.save(designation);

    }
}