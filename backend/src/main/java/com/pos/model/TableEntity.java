package main.java.com.pos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // AVAILABLE / OCCUPIED
}