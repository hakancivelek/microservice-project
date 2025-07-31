package com.hakancivelek.order.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        int stockQuantity
) {
}
