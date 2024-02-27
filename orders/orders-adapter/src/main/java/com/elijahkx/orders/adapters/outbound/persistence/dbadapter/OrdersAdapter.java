package com.elijahkx.orders.adapters.outbound.persistence.dbadapter;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.customers.adapters.outbound.rest.CustomersClient;
import com.elijahkx.orders.adapters.mappers.orders.OrdersMapper;
import com.elijahkx.orders.outbound.kafka.OrderEventPort;
import com.elijahkx.orders.outbound.persistence.OrdersPort;
import com.elijahkx.orders.adapters.outbound.persistence.repositories.OrdersRepository;

@Component
public class OrdersAdapter implements OrdersPort {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersRepository ordersRepository;

    private final CustomersClient customersClient;

    private final OrderEventPort orderEventPort;

    public OrdersAdapter(CustomersClient customersClient, OrderEventPort orderEventPort) {
        this.customersClient = customersClient;
        this.orderEventPort = orderEventPort;
    }

    @Override
    public List<OrderDomain> findByCriteria() {
        return ordersMapper.entityToDomain(ordersRepository.findAll());
    }

    @Override
    public Optional<OrderDomain> findById(Long id) {
        return ordersRepository.findById(id).map(ordersMapper::entityToDomain);
    }

    @Override
    public OrderDomain addOrder(OrderDomain order) {
        customersClient.findById(order.getCustomerId()).getBody();

        orderEventPort.produce(order);

        try {
            return ordersMapper.entityToDomain(ordersRepository.save(ordersMapper.domainToEntity(order)));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Customer with id " + order.getCustomerId() + " not found.");
        }
    }

    @Override
    public OrderDomain updateOrder(OrderDomain order) {
        return ordersMapper.entityToDomain(ordersRepository.save(ordersMapper.domainToEntity(order)));
    }

    @Override
    public void deleteOrder(Long id) {
        ordersRepository.deleteById(id);
    }
}
