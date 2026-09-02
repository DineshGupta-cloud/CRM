package com.CRM.service.Impl;

import com.CRM.constants.CustomerActivityCodes;
import com.CRM.dto.request.CustomerNoteRequest;
import com.CRM.dto.response.CustomerNoteResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerNote;
import com.CRM.entity.Employee;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.mapper.CustomerNoteMapper;
import com.CRM.repository.CustomerNoteRepository;
import com.CRM.repository.CustomerRepository;
import com.CRM.repository.EmployeeRepository;
import com.CRM.security.SecurityUtils;
import com.CRM.service.CustomerActivityService;
import com.CRM.service.CustomerNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerNoteServiceImpl implements CustomerNoteService {

    private final CustomerNoteRepository noteRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    private final CustomerNoteMapper mapper;

    private final CustomerActivityService customerActivityService;
    private final SecurityUtils securityUtils;

    @Override
    public CustomerNoteResponse save(CustomerNoteRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));

        Employee employee = employeeRepository.findById(
                        securityUtils.getCurrentEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        CustomerNote note = mapper.toEntity(
                request,
                customer,
                employee
        );

        note.setNoteCode(getNextNoteCode());

        CustomerNote saved = noteRepository.save(note);

        customerActivityService.logActivity(
                customer.getId(),
                CustomerActivityCodes.CUSTOMER_NOTE_ADDED,
                employee.getId(),
                "Customer Note Added",
                saved.getNote(),
                null,
                null,
                "CUSTOMER_NOTE",
                saved.getId(),
                true
        );

        return mapper.toResponse(saved);
    }
    @Override
    public CustomerNoteResponse update(Long id, CustomerNoteRequest request) {

        CustomerNote note = noteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer note not found."));

        note.setNote(request.getNote());

        if (request.getPinned() != null) {
            note.setPinned(request.getPinned());
        }

        if (request.getPrivateNote() != null) {
            note.setPrivateNote(request.getPrivateNote());
        }

        CustomerNote updated = noteRepository.save(note);

        customerActivityService.logActivity(
                updated.getCustomer().getId(),
                CustomerActivityCodes.CUSTOMER_NOTE_ADDED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Note Updated",
                updated.getNote(),
                null,
                null,
                "CUSTOMER_NOTE",
                updated.getId(),
                true
        );

        return mapper.toResponse(updated);
    }

    @Override
    public List<CustomerNoteResponse> getByCustomer(Long customerId) {

        return noteRepository
                .findByCustomerIdAndDeletedFalseOrderByCreatedDateDesc(customerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        CustomerNote note = noteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer note not found."));

        note.setDeleted(true);
        note.setActive(false);

        noteRepository.save(note);

        customerActivityService.logActivity(
                note.getCustomer().getId(),
                CustomerActivityCodes.CUSTOMER_NOTE_ADDED,
                securityUtils.getCurrentEmployeeId(),
                "Customer Note Deleted",
                note.getNote(),
                null,
                null,
                "CUSTOMER_NOTE",
                note.getId(),
                true
        );
    }

    private String getNextNoteCode() {

        return noteRepository.findTopByOrderByIdDesc()
                .map(note -> {
                    String code = note.getNoteCode().replace("CNT", "");
                    int next = Integer.parseInt(code) + 1;
                    return String.format("CNT%05d", next);
                })
                .orElse("CNT00001");
    }
}