package com.elijahkx.orders.adapters.outbound.persistence.dbadapter;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.customers.adapters.outbound.rest.CustomersClient;
import com.elijahkx.orders.adapters.mappers.orders.OrdersMapper;
import com.elijahkx.orders.outbound.kafka.OrderEventPort;
import com.elijahkx.orders.outbound.persistence.OrdersPort;
import com.elijahkx.orders.adapters.outbound.persistence.entities.orders.OrderEntity;
import com.elijahkx.orders.adapters.outbound.persistence.repositories.OrdersRepository;

@Component
public class OrdersAdapter implements OrdersPort {

    private OrdersMapper ordersMapper;

    private OrdersRepository ordersRepository;

    private final CustomersClient customersClient;

    private final OrderEventPort orderEventPort;

    public OrdersAdapter(
            OrdersMapper ordersMapper,
            OrdersRepository ordersRepository,
            CustomersClient customersClient,
            OrderEventPort orderEventPort) {

        this.ordersMapper = ordersMapper;
        this.ordersRepository = ordersRepository;
        this.customersClient = customersClient;
        this.orderEventPort = orderEventPort;
    }

    @Override
    public List<OrderDomain> findByCriteria(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> ordersPage = ordersRepository.findByCriteria(customerId, pageable);

        return ordersMapper.entityToDomain(ordersPage.getContent());
    }

    @Override
    public Optional<OrderDomain> findById(Long id) {
        return ordersRepository.findById(id).map(ordersMapper::entityToDomain);
    }

    @Override
    public OrderDomain addOrder(OrderDomain orderDomain) {
        try {
            customersClient.findById(orderDomain.getCustomerId()).getBody();

            OrderEntity savedOrderEntity = ordersRepository.save(ordersMapper.domainToEntity(orderDomain));

            orderEventPort.produce(orderDomain);

            return ordersMapper.entityToDomain(savedOrderEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Customer with id " + orderDomain.getCustomerId() + " not found.");
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
