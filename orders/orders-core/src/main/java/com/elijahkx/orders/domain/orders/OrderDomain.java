package com.elijahkx.orders.domain.orders;

import java.time.LocalDate;

import com.elijahkx.customers.rest.dto.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDomain {
    private Long id;

    private LocalDate createdDate;

    private Customer customer;
}
