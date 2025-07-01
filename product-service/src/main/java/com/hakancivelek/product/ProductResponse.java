package com.hakancivelek.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        int stockQuantity
) {
}
