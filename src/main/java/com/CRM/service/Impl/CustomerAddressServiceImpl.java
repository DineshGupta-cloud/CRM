package com.CRM.service.Impl;

import com.CRM.constants.CustomerActivityCodes;
import com.CRM.dto.request.CustomerAddressRequest;
import com.CRM.dto.response.CustomerAddressResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerAddress;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.CustomerAddressMapper;
import com.CRM.repository.CustomerAddressRepository;
import com.CRM.repository.CustomerRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.CustomerActivityService;
import com.CRM.service.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerAddressServiceImpl implements CustomerAddressService {

    private final CustomerAddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    private final CustomerAddressMapper mapper;

    private final CustomerActivityService customerActivityService;
    private final SecurityUtils securityUtils;

    @Override
    public CustomerAddressResponse save(CustomerAddressRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        CustomerAddress address = mapper.toEntity(request, customer);

        address.setAddressCode(getNextAddressCode());

        if (Boolean.TRUE.equals(request.getPrimaryAddress())) {
            clearPrimaryAddress(customer.getId());
        }

        CustomerAddress saved = addressRepository.save(address);

        customerActivityService.logActivity(
                customer.getId(),
                CustomerActivityCodes.ADDRESS_ADDED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Address Added",
                saved.getAddressType() + " address added.",
                null,
                null,
                "CUSTOMER_ADDRESS",
                saved.getId(),
                true
        );

        return mapper.toResponse(saved);
    }
    @Override
    public CustomerAddressResponse update(Long id,
                                          CustomerAddressRequest request) {

        CustomerAddress address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer address not found."));

        if (Boolean.TRUE.equals(request.getPrimaryAddress())) {
            clearPrimaryAddress(address.getCustomer().getId());
        }

        mapper.updateEntity(address, request);

        CustomerAddress updated = addressRepository.save(address);

        customerActivityService.logActivity(
                updated.getCustomer().getId(),
                CustomerActivityCodes.ADDRESS_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Address Updated",
                updated.getAddressType() + " address updated.",
                null,
                null,
                "CUSTOMER_ADDRESS",
                updated.getId(),
                true
        );

        return mapper.toResponse(updated);
    }

    @Override
    public List<CustomerAddressResponse> getByCustomer(Long customerId) {

        return addressRepository
                .findByCustomerIdAndDeletedFalseOrderByAddressTypeAsc(customerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        CustomerAddress address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer address not found."));

        address.setDeleted(true);
        address.setActive(false);

        addressRepository.save(address);

        customerActivityService.logActivity(
                address.getCustomer().getId(),
                CustomerActivityCodes.ADDRESS_UPDATED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Address Deleted",
                address.getAddressType() + " address deleted.",
                null,
                null,
                "CUSTOMER_ADDRESS",
                address.getId(),
                true
        );
    }

    private void clearPrimaryAddress(Long customerId) {

        addressRepository
                .findByCustomerIdAndDeletedFalseOrderByAddressTypeAsc(customerId)
                .forEach(address -> address.setPrimaryAddress(false));

        addressRepository.flush();
    }

    private String getNextAddressCode() {

        return addressRepository.findTopByOrderByIdDesc()
                .map(address -> {
                    String code = address.getAddressCode().replace("CAD", "");
                    int next = Integer.parseInt(code) + 1;
                    return String.format("CAD%05d", next);
                })
                .orElse("CAD00001");
    }
}
