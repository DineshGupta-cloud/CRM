package com.CRM.mapper;

import com.CRM.dto.request.CustomerRequest;
import com.CRM.dto.response.CustomerResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.Employee;
import com.CRM.entity.Industry;
import com.CRM.entity.Lead;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request,
                             Industry industry,
                             Employee employee,
                             Lead lead) {

        return Customer.builder()
                .customerName(request.getCustomerName())
                .companyName(request.getCompanyName())
                .designation(request.getDesignation())
                .email(request.getEmail())
                .alternateEmail(request.getAlternateEmail())
                .mobile(request.getMobile())
                .alternateMobile(request.getAlternateMobile())
                .phone(request.getPhone())
                .website(request.getWebsite())
                .gstNumber(request.getGstNumber())
                .panNumber(request.getPanNumber())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .customerSince(request.getCustomerSince())
                .creditLimit(request.getCreditLimit())
                .annualRevenue(request.getAnnualRevenue())
                .remarks(request.getRemarks())
                .industry(industry)
                .assignedEmployee(employee)
                .lead(lead)
                .active(request.getActive() == null ? true : request.getActive())
                .build();
    }

    public void updateEntity(Customer customer,
                             CustomerRequest request,
                             Industry industry,
                             Employee employee,
                             Lead lead) {

        customer.setCustomerName(request.getCustomerName());
        customer.setCompanyName(request.getCompanyName());
        customer.setDesignation(request.getDesignation());
        customer.setEmail(request.getEmail());
        customer.setAlternateEmail(request.getAlternateEmail());
        customer.setMobile(request.getMobile());
        customer.setAlternateMobile(request.getAlternateMobile());
        customer.setPhone(request.getPhone());
        customer.setWebsite(request.getWebsite());
        customer.setGstNumber(request.getGstNumber());
        customer.setPanNumber(request.getPanNumber());
        customer.setAddressLine1(request.getAddressLine1());
        customer.setAddressLine2(request.getAddressLine2());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setCountry(request.getCountry());
        customer.setPostalCode(request.getPostalCode());
        customer.setCustomerSince(request.getCustomerSince());
        customer.setCreditLimit(request.getCreditLimit());
        customer.setAnnualRevenue(request.getAnnualRevenue());
        customer.setRemarks(request.getRemarks());
        customer.setIndustry(industry);
        customer.setAssignedEmployee(employee);
        customer.setLead(lead);

        if (request.getActive() != null) {
            customer.setActive(request.getActive());
        }
    }

    public CustomerResponse toResponse(Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .customerCode(customer.getCustomerCode())
                .customerName(customer.getCustomerName())
                .companyName(customer.getCompanyName())
                .designation(customer.getDesignation())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .city(customer.getCity())
                .state(customer.getState())
                .country(customer.getCountry())
                .gstNumber(customer.getGstNumber())
                .panNumber(customer.getPanNumber())
                .industry(customer.getIndustry() != null
                        ? customer.getIndustry().getIndustryName()
                        : null)
                .assignedEmployee(customer.getAssignedEmployee() != null
                        ? customer.getAssignedEmployee().getFirstName() + " "
                        + customer.getAssignedEmployee().getLastName()
                        : null)
                .leadCode(customer.getLead() != null
                        ? customer.getLead().getLeadCode()
                        : null)
                .customerSince(customer.getCustomerSince())
                .creditLimit(customer.getCreditLimit())
                .annualRevenue(customer.getAnnualRevenue())
                .active(customer.getActive())
                .createdDate(customer.getCreatedDate())
                .build();
    }
}