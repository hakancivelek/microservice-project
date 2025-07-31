package com.hakancivelek.order;

import com.hakancivelek.order.product.ProductClientFacade;
import com.hakancivelek.order.product.ProductResponse;
import com.hakancivelek.order.user.UserClientFacade;
import com.hakancivelek.order.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClientFacade productClientFacade;
    private final UserClientFacade userClientFacade;

    public OrderService(OrderRepository orderRepository, ProductClientFacade productClientFacade, UserClientFacade userClientFacade) {
        this.orderRepository = orderRepository;
        this.productClientFacade = productClientFacade;
        this.userClientFacade = userClientFacade;
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
        checkProduct(order);
        checkUser(order);

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

    private void checkProduct(NewOrderRequest req) {
        ProductResponse product = productClientFacade.getProduct(req.productId());
        if (!Objects.equals(product.productId(), req.productId())) {
            throw new RuntimeException("checkProduct Product not found");
        }
    }

    private void checkUser(NewOrderRequest req) {
        UserResponse user = userClientFacade.getUser(req.userId());
        if (user.username().equals("0") || user.username().equals("-1")) {
            System.out.println("checkUser username: "  + user.username());
            throw new RuntimeException("checkUser User not found");
        }
    }

    public List<UserResponse> getAllUsersFromOrderService() {
        return userClientFacade.getAllUsers();
    }
}
