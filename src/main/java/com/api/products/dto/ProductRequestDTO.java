package com.api.products.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "createdBy is required")
    private String createdBy;

}