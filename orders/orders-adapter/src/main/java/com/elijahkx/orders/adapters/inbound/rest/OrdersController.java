package com.elijahkx.orders.adapters.inbound.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.orders.adapters.mappers.orders.OrdersMapper;
import com.elijahkx.orders.rest.api.OrdersApi;
import com.elijahkx.orders.rest.dto.Order;
import com.elijahkx.orders.service.orders.OrdersService;
import com.elijahkx.utils.ElijahErrorResponse;

import jakarta.validation.Valid;

@RestController
@Validated
public class OrdersController implements OrdersApi {

    private final OrdersService ordersService;

    private final OrdersMapper ordersMapper;

    public OrdersController(OrdersService ordersService, OrdersMapper ordersMapper) {
        this.ordersService = ordersService;
        this.ordersMapper = ordersMapper;
    }

    @Override
    public ResponseEntity<List<Order>> findByCriteria(Long id) {
        return ResponseEntity.ok(ordersMapper.domainToDto(ordersService.findByCriteria()));
    }

    @Override
    public ResponseEntity<Order> findByid(Long id) {
        Optional<OrderDomain> optionalOrderDomain = ordersService.findById(id);

        return optionalOrderDomain
                .map(optionalOrder -> ResponseEntity.ok(ordersMapper.domainToDto(optionalOrder)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Order> addOrder(@RequestBody @Valid Order order) {
        return ResponseEntity.ok(ordersMapper.domainToDto(ordersService.addOrder(ordersMapper.dtoToDomain(order))));
    }

    @Override
    public ResponseEntity<Object> updateOrder(@RequestBody @Valid Order order, @PathVariable Long id) {
        Optional<OrderDomain> optionalOrderDomain = ordersService.findById(id);

        if (optionalOrderDomain.isPresent()) {
            OrderDomain orderDomain = optionalOrderDomain.get();

            orderDomain.setCustomerId(order.getCustomerId());

            return ResponseEntity.ok(ordersMapper.domainToDto(orderDomain));

        } else {
            ElijahErrorResponse errorResponse = new ElijahErrorResponse(HttpStatus.NOT_FOUND, "Order not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<Object> deleteOrder(Long id) {
        Optional<OrderDomain> optionalOrderDomain = ordersService.findById(id);

        if (optionalOrderDomain.isPresent()) {
            ordersService.deleteOrder(id);

            return ResponseEntity.ok().build();
        } else {
            ElijahErrorResponse errorResponse = new ElijahErrorResponse(HttpStatus.NOT_FOUND, "Order not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
