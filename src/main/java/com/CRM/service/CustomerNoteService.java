package com.CRM.service;

import com.CRM.dto.request.CustomerNoteRequest;
import com.CRM.dto.response.CustomerNoteResponse;

import java.util.List;

public interface CustomerNoteService {

    CustomerNoteResponse save(CustomerNoteRequest request);

    CustomerNoteResponse update(Long id, CustomerNoteRequest request);

    List<CustomerNoteResponse> getByCustomer(Long customerId);

    void delete(Long id);

}