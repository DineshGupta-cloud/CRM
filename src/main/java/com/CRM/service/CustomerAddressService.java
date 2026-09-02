package com.CRM.service;

import com.CRM.dto.request.CustomerAddressRequest;
import com.CRM.dto.response.CustomerAddressResponse;

import java.util.List;

public interface CustomerAddressService {

    CustomerAddressResponse save(CustomerAddressRequest request);

    CustomerAddressResponse update(Long id, CustomerAddressRequest request);

    List<CustomerAddressResponse> getByCustomer(Long customerId);

    void delete(Long id);

}