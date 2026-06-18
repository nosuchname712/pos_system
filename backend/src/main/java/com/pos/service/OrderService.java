package com.pos.service;

import com.pos.enums.OrderStatus;
import com.pos.model.MenuItem;
import com.pos.model.Order;
import com.pos.model.OrderItem;
import com.pos.repository.MenuRepository;
import com.pos.repository.OrderItemRepository;
import com.pos.repository.OrderRepository;
import com.pos.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuRepository menuRepository;
    private final TableRepository tableRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        MenuRepository menuRepository,
                        TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.menuRepository = menuRepository;
        this.tableRepository = tableRepository;
    }

    // 1. GET OR CREATE ACTIVE ORDER (CORE POS LOGIC)
    public Order getOrCreateActiveOrder(Long tableId) {
        return orderRepository.findByTableIdAndStatus(tableId, OrderStatus.OPEN)
                .orElseGet(() -> {
                    Order order = new Order();
                    order.setTableId(tableId);
                    order.setStatus(OrderStatus.OPEN);
                    order.setTotalPrice(0.0);

                    tableRepository.findById(tableId).ifPresent(table -> {
                        table.setStatus("OCCUPIED");
                        tableRepository.save(table);
                    });

                    return orderRepository.save(order);
                });
    }

    // 2. ADD ITEM (MIDWAY ORDERING SUPPORT)
    public Order addItemToOrder(Long orderId, Long menuItemId, int quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new RuntimeException("Cannot modify closed order");
        }

        MenuItem menuItem = menuRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setMenuItemId(menuItemId);
        item.setQuantity(quantity);
        item.setPriceAtTime(menuItem.getPrice());

        orderItemRepository.save(item);

        recalculateTotal(orderId);

        return order;
    }

    // 3. REMOVE ITEM
    public Order removeItem(Long orderItemId) {

        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Long orderId = item.getOrderId();

        orderItemRepository.delete(item);

        recalculateTotal(orderId);

        return orderRepository.findById(orderId).get();
    }

    // 4. RECALCULATE TOTAL 
    private void recalculateTotal(Long orderId) {

        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);

        double total = items.stream()
                .mapToDouble(i -> i.getPriceAtTime() * i.getQuantity())
                .sum();

        Order order = orderRepository.findById(orderId).get();
        order.setTotalPrice(total);

        orderRepository.save(order);
    }

    // 5. CHECKOUT ORDER
    public Order checkoutOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new RuntimeException("Order already closed");
        }

        order.setStatus(OrderStatus.CHECKED_OUT);

        return orderRepository.save(order);
    }

    // 6. MARK AS PAID (FINAL STEP)
    public Order markAsPaid(Long orderId, String paymentMethod) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.PAID);
        order.setPaymentMethod(paymentMethod);

        // free table
        tableRepository.findById(order.getTableId()).ifPresent(table -> {
            table.setStatus("AVAILABLE");
            tableRepository.save(table);
        });

        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}