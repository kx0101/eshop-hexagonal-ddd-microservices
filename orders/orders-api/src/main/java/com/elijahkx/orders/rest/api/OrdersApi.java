package com.elijahkx.orders.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.elijahkx.orders.rest.dto.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "orders", description = "the orders API")
public interface OrdersApi {
    String BASE_URL = "/orders";

    @Operation(summary = "Get all orders")
    @GetMapping(BASE_URL)
    ResponseEntity<List<Order>> findByCriteria(
            @Parameter(in = ParameterIn.QUERY, description = "The id of the customer") 
            @RequestParam(required = false) Long id
    );

    @Operation(summary = "Get all orders")
    @GetMapping(BASE_URL + "/{id}")
    ResponseEntity<Order> findByid(
            @Parameter(description = "The id of the customer") 
            @PathVariable(required = false) Long id
    );

    @Operation(summary = "Create a new order")
    @PostMapping(BASE_URL)
    ResponseEntity<Order> addOrder(@RequestBody @Valid Order customer);

    @Operation(summary = "Update an order by id")
    @PutMapping(BASE_URL + "/{id}")
    ResponseEntity<Object> updateOrder(
            @RequestBody @Valid Order customer,
            @PathVariable(required = true) Long id
    );

    @Operation(summary = "Delete an order by id")
    @DeleteMapping(BASE_URL + "/{id}")
    ResponseEntity<Object> deleteOrder(
            @Parameter(description = "The id of the order") 
            @PathVariable(required = true) Long id
    );
}
