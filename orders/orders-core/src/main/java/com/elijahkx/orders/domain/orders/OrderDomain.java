package com.elijahkx.orders.domain.orders;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDomain {
    private Long id;

    private LocalDate createdDate;

    private Long customerId;
}
