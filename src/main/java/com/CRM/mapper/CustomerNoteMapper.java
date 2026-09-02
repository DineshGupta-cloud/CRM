package com.CRM.mapper;

import com.CRM.dto.request.CustomerNoteRequest;
import com.CRM.dto.response.CustomerNoteResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerNote;
import com.CRM.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class CustomerNoteMapper {

    public CustomerNote toEntity(CustomerNoteRequest request,
                                 Customer customer,
                                 Employee employee) {

        return CustomerNote.builder()
                .customer(customer)
                .employee(employee)
                .note(request.getNote())
                .pinned(request.getPinned() == null ? false : request.getPinned())
                .privateNote(request.getPrivateNote() == null ? false : request.getPrivateNote())
                .active(true)
                .deleted(false)
                .build();
    }

    public CustomerNoteResponse toResponse(CustomerNote note) {

        return CustomerNoteResponse.builder()
                .id(note.getId())
                .noteCode(note.getNoteCode())
                .customerCode(note.getCustomer().getCustomerCode())
                .customerName(note.getCustomer().getCustomerName())
                .employeeName(note.getEmployee().getFirstName() + " "
                        + note.getEmployee().getLastName())
                .note(note.getNote())
                .pinned(note.getPinned())
                .privateNote(note.getPrivateNote())
                .createdDate(note.getCreatedDate())
                .build();
    }
}