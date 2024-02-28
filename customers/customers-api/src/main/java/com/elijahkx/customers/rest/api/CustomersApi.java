package com.elijahkx.customers.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.elijahkx.customers.rest.dto.Customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Tag(name = "customers", description = "the customers API")
public interface CustomersApi {
    String BASE_URL = "/customers";

    @Operation(summary = "Get all customers")
    @GetMapping(BASE_URL)
    ResponseEntity<List<Customer>> findByCriteria(
            @Parameter(in = ParameterIn.QUERY, description = "The name of the customer") 
            @RequestParam(required = false) String name,

            @Parameter(in = ParameterIn.QUERY, description = "The email of the customer")
            @RequestParam(required = false) String email,

            @Parameter(in = ParameterIn.QUERY, description = "Page") 
            @RequestParam(defaultValue = "0", required = false) @Min(0) int page,

            @Parameter(in = ParameterIn.QUERY, description = "Size of each page") 
            @RequestParam(defaultValue = "10", required = false) @Min(1) @Max(10) int size
    );

    @Operation(summary = "Get a customer by id")
    @GetMapping(BASE_URL + "/{id}")
    ResponseEntity<Customer> findById(
            @Parameter(description = "The id of the customer") 
            @PathVariable(required = true) Long id
    );

    @Operation(summary = "Create a new customer")
    @PostMapping(BASE_URL)
    ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer);

    @Operation(summary = "Update a customer by id")
    @PutMapping(BASE_URL + "/{id}")
    ResponseEntity<Object> updateCustomer(
            @RequestBody @Valid Customer customer,
            @PathVariable(required = true) Long id
    );

    @Operation(summary = "Delete a customer by email")
    @DeleteMapping(BASE_URL + "/{email}")
    ResponseEntity<Object> deleteCustomerByEmail(
            @Parameter(description = "The email of the customer") 
            @PathVariable(required = true) String email
    );
}
