package com.elijahkx.customers.domain.customers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDomain {
    private Long id;

    private String name;

    private String email;
}
