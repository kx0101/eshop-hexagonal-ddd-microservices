package com.elijahkx.orders.rest.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.elijahkx.customers.rest.dto.Customer;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    @NotNull
    @DateTimeFormat
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
