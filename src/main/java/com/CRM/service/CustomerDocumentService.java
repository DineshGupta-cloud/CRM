package com.CRM.service;

import com.CRM.dto.request.CustomerDocumentRequest;
import com.CRM.dto.response.CustomerDocumentResponse;

import java.util.List;

public interface CustomerDocumentService {

    CustomerDocumentResponse save(CustomerDocumentRequest request);

    CustomerDocumentResponse update(Long id, CustomerDocumentRequest request);

    CustomerDocumentResponse getById(Long id);

    List<CustomerDocumentResponse> getByCustomer(Long customerId);

    void delete(Long id);

}