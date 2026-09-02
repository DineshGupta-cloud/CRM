package com.CRM.mapper;

import com.CRM.dto.request.CustomerDocumentRequest;
import com.CRM.dto.response.CustomerDocumentResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerDocument;
import org.springframework.stereotype.Component;

@Component
public class CustomerDocumentMapper {

    public CustomerDocument toEntity(CustomerDocumentRequest request,
                                     Customer customer) {

        return CustomerDocument.builder()
                .customer(customer)
                .documentName(request.getDocumentName())
                .documentType(request.getDocumentType())
                .fileName(request.getFileName())
                .filePath(request.getFilePath())
                .fileExtension(request.getFileExtension())
                .fileSize(request.getFileSize())
                .remarks(request.getRemarks())
                .active(true)
                .deleted(false)
                .build();
    }

    public void updateEntity(CustomerDocument document,
                             CustomerDocumentRequest request) {

        document.setDocumentName(request.getDocumentName());
        document.setDocumentType(request.getDocumentType());
        document.setFileName(request.getFileName());
        document.setFilePath(request.getFilePath());
        document.setFileExtension(request.getFileExtension());
        document.setFileSize(request.getFileSize());
        document.setRemarks(request.getRemarks());
    }

    public CustomerDocumentResponse toResponse(CustomerDocument document) {

        return CustomerDocumentResponse.builder()
                .id(document.getId())
                .documentCode(document.getDocumentCode())
                .customerCode(document.getCustomer().getCustomerCode())
                .customerName(document.getCustomer().getCustomerName())
                .documentName(document.getDocumentName())
                .documentType(document.getDocumentType())
                .fileName(document.getFileName())
                .filePath(document.getFilePath())
                .fileExtension(document.getFileExtension())
                .fileSize(document.getFileSize())
                .remarks(document.getRemarks())
                .createdDate(document.getCreatedDate())
                .active(document.getActive())
                .build();
    }
}