package com.CRM.service.Impl;

import com.CRM.constants.CustomerActivityCodes;
import com.CRM.dto.request.CustomerDocumentRequest;
import com.CRM.dto.response.CustomerDocumentResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerDocument;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.CustomerDocumentMapper;
import com.CRM.repository.CustomerDocumentRepository;
import com.CRM.repository.CustomerRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.CustomerActivityService;
import com.CRM.service.CustomerDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDocumentServiceImpl implements CustomerDocumentService {

    private final CustomerDocumentRepository documentRepository;
    private final CustomerRepository customerRepository;
    private final CustomerDocumentMapper mapper;
    private final CustomerActivityService customerActivityService;
    private final SecurityUtils securityUtils;

    @Override
    public CustomerDocumentResponse save(CustomerDocumentRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        CustomerDocument document = mapper.toEntity(request, customer);

        document.setDocumentCode(getNextDocumentCode());

        CustomerDocument saved = documentRepository.save(document);

        customerActivityService.logActivity(
                customer.getId(),
                CustomerActivityCodes.DOCUMENT_ADDED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Document Added",
                saved.getDocumentName(),
                null,
                null,
                "CUSTOMER_DOCUMENT",
                saved.getId(),
                true
        );

        return mapper.toResponse(saved);
    }

    @Override
    public CustomerDocumentResponse update(Long id, CustomerDocumentRequest request) {

        CustomerDocument document = documentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer document not found."));

        mapper.updateEntity(document, request);

        CustomerDocument updated = documentRepository.save(document);

        customerActivityService.logActivity(
                updated.getCustomer().getId(),
                CustomerActivityCodes.DOCUMENT_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Document Updated",
                updated.getDocumentName(),
                null,
                null,
                "CUSTOMER_DOCUMENT",
                updated.getId(),
                true
        );

        return mapper.toResponse(updated);
    }

    @Override
    public CustomerDocumentResponse getById(Long id) {

        return mapper.toResponse(
                documentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Customer document not found."))
        );
    }

    @Override
    public List<CustomerDocumentResponse> getByCustomer(Long customerId) {

        return documentRepository
                .findByCustomerIdAndDeletedFalseOrderByCreatedDateDesc(customerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        CustomerDocument document = documentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer document not found."));

        document.setDeleted(true);
        document.setActive(false);

        documentRepository.save(document);

        customerActivityService.logActivity(
                document.getCustomer().getId(),
                CustomerActivityCodes.DOCUMENT_DELETED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Document Deleted",
                document.getDocumentName(),
                null,
                null,
                "CUSTOMER_DOCUMENT",
                document.getId(),
                true
        );
    }

    private String getNextDocumentCode() {

        return documentRepository.findTopByOrderByIdDesc()
                .map(document -> {
                    String code = document.getDocumentCode().replace("CDOC", "");
                    int next = Integer.parseInt(code) + 1;
                    return String.format("CDOC%05d", next);
                })
                .orElse("CDOC00001");
    }
}