package com.CRM.mapper;

import com.CRM.dto.request.CustomerContactRequest;
import com.CRM.dto.response.CustomerContactResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerContact;
import org.springframework.stereotype.Component;

@Component
public class CustomerContactMapper {

    public CustomerContact toEntity(CustomerContactRequest request,
                                    Customer customer) {

        return CustomerContact.builder()
                .customer(customer)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .designation(request.getDesignation())
                .department(request.getDepartment())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .phone(request.getPhone())
                .primaryContact(request.getPrimaryContact() == null
                        ? false
                        : request.getPrimaryContact())
                .active(true)
                .deleted(false)
                .build();
    }

    public void updateEntity(CustomerContact contact,
                             CustomerContactRequest request) {

        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setDesignation(request.getDesignation());
        contact.setDepartment(request.getDepartment());
        contact.setEmail(request.getEmail());
        contact.setMobile(request.getMobile());
        contact.setPhone(request.getPhone());

        if (request.getPrimaryContact() != null) {
            contact.setPrimaryContact(request.getPrimaryContact());
        }
    }

    public CustomerContactResponse toResponse(CustomerContact contact) {

        return CustomerContactResponse.builder()
                .id(contact.getId())
                .contactCode(contact.getContactCode())
                .customerCode(contact.getCustomer().getCustomerCode())
                .customerName(contact.getCustomer().getCustomerName())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .designation(contact.getDesignation())
                .department(contact.getDepartment())
                .email(contact.getEmail())
                .mobile(contact.getMobile())
                .phone(contact.getPhone())
                .primaryContact(contact.getPrimaryContact())
                .active(contact.getActive())
                .build();
    }
}