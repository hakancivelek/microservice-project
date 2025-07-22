package com.hakancivelek.order;

public record NewOrderRequest(
        Long productId,
        Long userId,
        Integer quantity
) {
}
