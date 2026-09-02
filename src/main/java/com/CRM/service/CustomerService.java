package com.CRM.service;

import com.CRM.dto.request.CustomerRequest;
import com.CRM.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse save(CustomerRequest request);

    CustomerResponse update(Long id, CustomerRequest request);

    CustomerResponse getById(Long id);

    List<CustomerResponse> getAll();

    void delete(Long id);

    CustomerResponse convertLead(Long leadId);

}