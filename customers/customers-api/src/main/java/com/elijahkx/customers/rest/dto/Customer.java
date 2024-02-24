package com.elijahkx.customers.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long Id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 10, message = "Name must be between 1 and 10 characters")
    private String name;

    @Email
    @Size(max = 50, message = "Email must be at most 50 characters")
    @NotNull(message = "Email cannot be null")
    private String email;
}
