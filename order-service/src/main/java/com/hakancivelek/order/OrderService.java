package com.hakancivelek.order;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponse> getAllOrders() {
        var orderEntities = orderRepository.findAll();
        return orderEntities.stream().map(this::toResponse).toList();
    }

    private OrderResponse toResponse(OrderEntity e) {
        return new OrderResponse(
                e.getId(),
                e.getProductId(),
                e.getUserId(),
                e.getQuantity()
        );
    }

    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public OrderResponse createOrder(NewOrderRequest order) {
        var orderEntity = new OrderEntity();
        orderEntity.setQuantity(order.quantity());
        orderEntity.setProductId(order.productId());
        orderEntity.setUserId(order.userId());
        return toResponse(orderRepository.save(orderEntity));
    }

    public void deleteOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}
