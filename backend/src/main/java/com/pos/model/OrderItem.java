package main.java.com.pos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long menuItemId;
    private int quantity;
    private double price;

    @ManyToOne
    private Order order;
}