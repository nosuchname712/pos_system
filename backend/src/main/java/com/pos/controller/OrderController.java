package com.pos.controller;

import com.pos.model.Order;
import com.pos.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1. Get or create active order for a table
    @PostMapping("/table/{tableId}")
    public Order getOrCreateActiveOrder(@PathVariable Long tableId) {
        return orderService.getOrCreateActiveOrder(tableId);
    }

    // 2. Add item to order (MIDWAY ORDERING SUPPORT)
    @PostMapping("/{orderId}/items")
    public Order addItemToOrder(
            @PathVariable Long orderId,
            @RequestParam Long menuItemId,
            @RequestParam int quantity
    ) {
        return orderService.addItemToOrder(orderId, menuItemId, quantity);
    }

    // 3. Remove item from order
    @DeleteMapping("/items/{orderItemId}")
    public Order removeItem(@PathVariable Long orderItemId) {
        return orderService.removeItem(orderItemId);
    }

    // 4. Checkout order (lock order)
    @PostMapping("/{orderId}/checkout")
    public Order checkoutOrder(@PathVariable Long orderId) {
        return orderService.checkoutOrder(orderId);
    }

    // 5. Payment (final step)
    @PostMapping("/{orderId}/pay")
    public Order payOrder(
            @PathVariable Long orderId,
            @RequestParam String paymentMethod
    ) {
        return orderService.markAsPaid(orderId, paymentMethod);
    }

    // 6. Get order details
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
}