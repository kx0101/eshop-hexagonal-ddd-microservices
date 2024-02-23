package com.elijahkx.customers.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Size(min = 1, max = 10)
    private String name;

    @NotNull
    @Size(min = 1, max = 10)
    private String email;
}
