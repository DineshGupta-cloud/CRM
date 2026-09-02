package com.CRM.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDocumentResponse {

    private Long id;

    private String documentCode;

    private String customerCode;

    private String customerName;

    private String documentName;

    private String documentType;

    private String fileName;

    private String filePath;

    private String fileExtension;

    private Long fileSize;

    private String remarks;

    private LocalDateTime createdDate;

    private Boolean active;
}