package com.pos.controller;

import com.pos.model.Order;
import com.pos.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/table/{tableId}")
    public Order createOrder(@PathVariable Long tableId) {
        return service.createOrder(tableId);
    }
}