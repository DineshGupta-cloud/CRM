package com.CRM.service;

import com.CRM.dto.request.CustomerContactRequest;
import com.CRM.dto.response.CustomerContactResponse;

import java.util.List;

public interface CustomerContactService {

    CustomerContactResponse save(CustomerContactRequest request);

    CustomerContactResponse update(Long id, CustomerContactRequest request);

    List<CustomerContactResponse> getByCustomer(Long customerId);

    void delete(Long id);

}