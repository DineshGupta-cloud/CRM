package com.CRM.mapper;

import com.CRM.dto.request.CustomerAddressRequest;
import com.CRM.dto.response.CustomerAddressResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerAddress;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressMapper {

    public CustomerAddress toEntity(CustomerAddressRequest request,
                                    Customer customer) {

        return CustomerAddress.builder()
                .customer(customer)
                .addressType(request.getAddressType())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .primaryAddress(request.getPrimaryAddress() == null
                        ? false
                        : request.getPrimaryAddress())
                .active(true)
                .deleted(false)
                .build();
    }

    public void updateEntity(CustomerAddress address,
                             CustomerAddressRequest request) {

        address.setAddressType(request.getAddressType());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        if (request.getPrimaryAddress() != null) {
            address.setPrimaryAddress(request.getPrimaryAddress());
        }
    }

    public CustomerAddressResponse toResponse(CustomerAddress address) {

        return CustomerAddressResponse.builder()
                .id(address.getId())
                .addressCode(address.getAddressCode())
                .customerCode(address.getCustomer().getCustomerCode())
                .customerName(address.getCustomer().getCustomerName())
                .addressType(address.getAddressType())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .primaryAddress(address.getPrimaryAddress())
                .active(address.getActive())
                .build();
    }
}