package com.api.products.service;

import com.api.products.dto.ProductRequestDTO;
import com.api.products.dto.ProductResponseDTO;
import com.api.products.entity.Product;
import com.api.products.exception.ProductNotFoundException;
import com.api.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository productRepository;

    // CREATE
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setProductName(requestDTO.getProductName());
        product.setCreatedBy(requestDTO.getCreatedBy());
        product.setCreatedOn(LocalDateTime.now());

        Product saved = productRepository.save(product);
        return mapToResponseDTO(saved);
    }

    // READ (single)
    public ProductResponseDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return mapToResponseDTO(product);
    }

    // READ (all, paginated)
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(this::mapToResponseDTO);
    }

    // UPDATE
    public ProductResponseDTO updateProduct(Integer id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setProductName(requestDTO.getProductName());
        product.setModifiedBy(requestDTO.getCreatedBy());
        product.setModifiedOn(LocalDateTime.now());

        Product updated = productRepository.save(product);
        return mapToResponseDTO(updated);
    }

    // DELETE
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    // HELPER: Entity -> DTO mapping
    private ProductResponseDTO mapToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getCreatedBy(),
                product.getCreatedOn(),
                product.getModifiedBy(),
                product.getModifiedOn()
        );
    }

}