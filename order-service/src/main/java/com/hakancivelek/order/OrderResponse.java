package com.hakancivelek.order;

public record OrderResponse(
        Long orderId,
        Long productId,
        Long userId,
        Integer quantity
) {
}