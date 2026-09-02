package com.CRM.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDocumentRequest {

    @NotNull(message = "Customer is required.")
    private Long customerId;

    @NotBlank(message = "Document name is required.")
    private String documentName;

    @NotBlank(message = "Document type is required.")
    private String documentType;

    @NotBlank(message = "File name is required.")
    private String fileName;

    @NotBlank(message = "File path is required.")
    private String filePath;

    private String fileExtension;

    private Long fileSize;

    private String remarks;
}