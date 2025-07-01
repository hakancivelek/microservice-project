package com.hakancivelek.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<ProductResponse> getProductById(Long productId) {
        return productRepository.findById(productId).map(this::toResponse);
    }

    @Transactional
    public ProductResponse createProduct(NewProductRequest newProductRequest) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(newProductRequest.name());
        productEntity.setDescription(newProductRequest.description());
        productEntity.setPrice(newProductRequest.price());
        productEntity.setStockQuantity(newProductRequest.stockQuantity());

        return toResponse(productRepository.save(productEntity));
    }

    private ProductResponse toResponse(ProductEntity entity) {
        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStockQuantity()
        );
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public boolean deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
}
