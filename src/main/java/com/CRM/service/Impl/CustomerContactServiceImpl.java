package com.CRM.service.Impl;

import com.CRM.constants.CustomerActivityCodes;
import com.CRM.dto.request.CustomerContactRequest;
import com.CRM.dto.response.CustomerContactResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerContact;
import com.CRM.exception.DuplicateResourceException;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.CustomerContactMapper;
import com.CRM.repository.CustomerContactRepository;
import com.CRM.repository.CustomerRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.CustomerActivityService;
import com.CRM.service.CustomerContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerContactServiceImpl implements CustomerContactService {

    private final CustomerContactRepository contactRepository;
    private final CustomerRepository customerRepository;
    private final CustomerContactMapper mapper;

    private final CustomerActivityService customerActivityService;
    private final SecurityUtils securityUtils;

    @Override
    public CustomerContactResponse save(CustomerContactRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        validateDuplicate(request);

        CustomerContact contact = mapper.toEntity(request, customer);

        contact.setContactCode(getNextContactCode());

        if (Boolean.TRUE.equals(request.getPrimaryContact())) {
            clearPrimaryContact(customer.getId());
        }

        CustomerContact saved = contactRepository.save(contact);

        customerActivityService.logActivity(
                customer.getId(),
                CustomerActivityCodes.CONTACT_ADDED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Contact Added",
                saved.getFirstName() + " " + saved.getLastName(),
                null,
                null,
                "CUSTOMER_CONTACT",
                saved.getId(),
                true
        );

        return mapper.toResponse(saved);
    }

    @Override
    public CustomerContactResponse update(Long id, CustomerContactRequest request) {

        CustomerContact contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer contact not found."));

        validateDuplicate(request, id);

        if (Boolean.TRUE.equals(request.getPrimaryContact())) {
            clearPrimaryContact(contact.getCustomer().getId());
        }

        mapper.updateEntity(contact, request);

        CustomerContact updated = contactRepository.save(contact);

        customerActivityService.logActivity(
                updated.getCustomer().getId(),
                CustomerActivityCodes.CONTACT_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Contact Updated",
                updated.getFirstName() + " " + updated.getLastName(),
                null,
                null,
                "CUSTOMER_CONTACT",
                updated.getId(),
                true
        );

        return mapper.toResponse(updated);
    }

    @Override
    public List<CustomerContactResponse> getByCustomer(Long customerId) {

        return contactRepository
                .findByCustomerIdAndDeletedFalseOrderByFirstNameAsc(customerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        CustomerContact contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer contact not found."));

        contact.setDeleted(true);
        contact.setActive(false);

        contactRepository.save(contact);

        customerActivityService.logActivity(
                contact.getCustomer().getId(),
                CustomerActivityCodes.CONTACT_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Contact Deleted",
                contact.getFirstName() + " " + contact.getLastName(),
                null,
                null,
                "CUSTOMER_CONTACT",
                contact.getId(),
                true
        );
    }

    private void validateDuplicate(CustomerContactRequest request) {
        validateDuplicate(request, null);
    }

    private void validateDuplicate(CustomerContactRequest request, Long contactId) {

        if (request.getEmail() != null && !request.getEmail().isBlank()) {

            contactRepository.findByEmail(request.getEmail())
                    .filter(contact -> !contact.getId().equals(contactId))
                    .ifPresent(contact -> {
                        throw new DuplicateResourceException(
                                "Contact already exists with email.");
                    });
        }

        if (request.getMobile() != null && !request.getMobile().isBlank()) {

            contactRepository.findByMobile(request.getMobile())
                    .filter(contact -> !contact.getId().equals(contactId))
                    .ifPresent(contact -> {
                        throw new DuplicateResourceException(
                                "Contact already exists with mobile.");
                    });
        }
    }

    private void clearPrimaryContact(Long customerId) {

        contactRepository
                .findByCustomerIdAndDeletedFalseOrderByFirstNameAsc(customerId)
                .forEach(contact -> contact.setPrimaryContact(false));

        contactRepository.flush();
    }

    private String getNextContactCode() {

        return contactRepository.findTopByOrderByIdDesc()
                .map(contact -> {
                    String code = contact.getContactCode().replace("CCT", "");
                    int next = Integer.parseInt(code) + 1;
                    return String.format("CCT%05d", next);
                })
                .orElse("CCT00001");
    }
}