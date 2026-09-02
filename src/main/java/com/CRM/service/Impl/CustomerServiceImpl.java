package com.CRM.service.Impl;

import com.CRM.constants.ActivityCodes;
import com.CRM.constants.CustomerActivityCodes;
import com.CRM.dto.request.CustomerRequest;
import com.CRM.dto.response.CustomerResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.Employee;
import com.CRM.entity.Industry;
import com.CRM.entity.Lead;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.CustomerMapper;
import com.CRM.repository.*;
import com.CRM.security.SecurityUtils;
import com.CRM.service.CustomerActivityService;
import com.CRM.service.CustomerService;
import com.CRM.service.LeadActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final IndustryRepository industryRepository;
    private final EmployeeRepository employeeRepository;
    private final LeadRepository leadRepository;
    private final LeadActivityService activityService;
    private final SecurityUtils securityUtils;

    private final CustomerActivityService customerActivityService;


    private final CustomerMapper mapper;

    @Override
    public CustomerResponse save(CustomerRequest request) {

        validateDuplicate(request);

        Industry industry = getIndustry(request.getIndustryId());
        Employee employee = getEmployee(request.getAssignedEmployeeId());
        Lead lead = getLead(request.getLeadId());

        Customer customer = mapper.toEntity(
                request,
                industry,
                employee,
                lead
        );

        customer.setCustomerCode(getNextCustomerCode());

        Customer saved = customerRepository.save(customer);

        customerActivityService.logActivity(
                saved.getId(),
                CustomerActivityCodes.CUSTOMER_CREATED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Created",
                "Customer created successfully.",
                null,
                null,
                "CUSTOMER",
                saved.getId(),
                true
        );

        return mapper.toResponse(saved);
    }

    @Override
    public CustomerResponse update(Long id, CustomerRequest request) {



        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));



        validateDuplicate(request);

        Industry industry = getIndustry(request.getIndustryId());
        Employee employee = getEmployee(request.getAssignedEmployeeId());
        Lead lead = getLead(request.getLeadId());

        String oldEmployee = customer.getAssignedEmployee() != null
                ? customer.getAssignedEmployee().getFirstName() + " "
                + customer.getAssignedEmployee().getLastName()
                : null;

        mapper.updateEntity(
                customer,
                request,
                industry,
                employee,
                lead
        );
        Customer updated = customerRepository.save(customer);

        customerActivityService.logActivity(
                updated.getId(),
                CustomerActivityCodes.CUSTOMER_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Updated",
                "Customer details updated successfully.",
                null,
                null,
                "CUSTOMER",
                updated.getId(),
                true
        );
        return mapper.toResponse(
                customerRepository.save(customer)
        );
    }

    @Override
    public CustomerResponse getById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        return mapper.toResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAll() {

        return customerRepository.findAll()
                .stream()
                .filter(customer -> !Boolean.TRUE.equals(customer.getDeleted()))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        customer.setActive(false);
        customer.setDeleted(true);

        customerRepository.save(customer);
    }

    // ==========================================================
    // Helper Methods
    // ==========================================================

    private Industry getIndustry(Long id) {

        if (id == null) {
            return null;
        }

        return industryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Industry not found."));
    }

    private Employee getEmployee(Long id) {

        if (id == null) {
            return null;
        }

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));
    }

    private Lead getLead(Long id) {

        if (id == null) {
            return null;
        }

        return leadRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));
    }

    private void validateDuplicate(CustomerRequest request) {

        if (request.getEmail() != null &&
                customerRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Customer already exists with email.");
        }

        if (request.getMobile() != null &&
                customerRepository.existsByMobile(request.getMobile())) {

            throw new DuplicateResourceException(
                    "Customer already exists with mobile.");
        }

        if (request.getGstNumber() != null &&
                customerRepository.existsByGstNumber(request.getGstNumber())) {

            throw new DuplicateResourceException(
                    "Customer already exists with GST Number.");
        }
    }

    private String getNextCustomerCode() {

        long count = customerRepository.count() + 1;

        return String.format("CUST%05d", count);
    }

    @Override
    @Transactional
    public CustomerResponse convertLead(Long leadId) {

        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found."));

        if (Boolean.TRUE.equals(lead.getConverted())) {
            throw new DuplicateResourceException(
                    "Lead has already been converted.");
        }

        Customer customer = Customer.builder()
                .customerCode(getNextCustomerCode())
                .customerName(
                        lead.getFirstName() + " " + lead.getLastName())
                .companyName(lead.getCompanyName())
                .designation(lead.getDesignation())
                .email(lead.getEmail())
                .alternateEmail(lead.getAlternateEmail())
                .mobile(lead.getMobile())
                .alternateMobile(lead.getAlternateMobile())
                .phone(lead.getPhone())
                .website(lead.getWebsite())
                .addressLine1(lead.getAddressLine1())
                .addressLine2(lead.getAddressLine2())
                .city(lead.getCity())
                .state(lead.getState())
                .country(lead.getCountry())
                .postalCode(lead.getPostalCode())
                .industry(lead.getIndustry())
                .assignedEmployee(lead.getAssignedEmployee())
                .lead(lead)
                .remarks(lead.getRemarks())
                .active(true)
                .deleted(false)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        customerActivityService.logActivity(
                savedCustomer.getId(),
                CustomerActivityCodes.CUSTOMER_CREATED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Created",
                "Customer created from Lead " + lead.getLeadCode(),
                null,
                null,
                "CUSTOMER",
                savedCustomer.getId(),
                true
        );

        lead.setConverted(true);
        lead.setConvertedDate(LocalDateTime.now());
        lead.setConvertedCustomer(savedCustomer);

        leadRepository.save(lead);

        activityService.logActivity(
                lead.getId(),
                ActivityCodes.LEAD_CONVERTED,
                securityUtils.getCurrentEmployeeId(),
                "Lead Converted",
                "Lead converted to Customer " + savedCustomer.getCustomerCode(),
                null,
                null,
                "CUSTOMER",
                savedCustomer.getId(),
                true
        );

        return mapper.toResponse(savedCustomer);
    }
}