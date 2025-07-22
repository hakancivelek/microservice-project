package com.hakancivelek.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderEntitiy>> getAllOrders() {
        List<OrderEntitiy> orderEntities = orderService.getAllOrders();

        if (orderEntities.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.ok(orderEntities); // 200
        }
    }

}
