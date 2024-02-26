package com.elijahkx.orders.adapters.outbound.persistence.entities.orders;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time_created")
    private LocalDate createdDate;

    @Column(name = "customer_id")
    private Long customerId;
}
