package com.CRM.service.Impl;

import com.CRM.dto.request.OpportunityProductRequest;
import com.CRM.dto.response.OpportunityProductResponse;
import com.CRM.entity.Opportunity;
import com.CRM.entity.OpportunityProduct;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.OpportunityProductMapper;
import com.CRM.repository.OpportunityProductRepository;
import com.CRM.repository.OpportunityRepository;
import com.CRM.service.OpportunityAggregationService;
import com.CRM.service.OpportunityProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityProductServiceImpl implements OpportunityProductService {

    private final OpportunityProductRepository productRepository;
    private final OpportunityRepository opportunityRepository;
    private final OpportunityProductMapper mapper;
    private final OpportunityAggregationService aggregationService;



    @Override
    public OpportunityProductResponse add(OpportunityProductRequest request) {

        Opportunity opportunity = opportunityRepository.findById(request.getOpportunityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opportunity not found."));

        OpportunityProduct product = mapper.toEntity(request, opportunity);

        OpportunityProduct saved = productRepository.save(product);

        // 🔥 AUTO RECALCULATION
        aggregationService.recalculateOpportunityValue(opportunity.getId());

        return mapper.toResponse(saved);
    }


    @Override
    public OpportunityProductResponse update(Long id, OpportunityProductRequest request) {

        OpportunityProduct product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        mapper.updateEntity(product, request);

        OpportunityProduct updated = productRepository.save(product);

        // 🔥 AUTO RECALCULATION
        aggregationService.recalculateOpportunityValue(
                updated.getOpportunity().getId()
        );

        return mapper.toResponse(updated);
    }

    @Override
    public List<OpportunityProductResponse> getByOpportunity(Long opportunityId) {

        return productRepository.findByOpportunityIdAndDeletedFalse(opportunityId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    @Override
    public void delete(Long id) {

        OpportunityProduct product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        product.setDeleted(true);
        product.setActive(false);

        productRepository.save(product);

        // 🔥 AUTO RECALCULATION
        aggregationService.recalculateOpportunityValue(
                product.getOpportunity().getId()
        );
    }
}