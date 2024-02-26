package com.elijahkx.orders.adapters.outbound.persistence.dbadapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.orders.adapters.mappers.orders.OrdersMapper;
import com.elijahkx.orders.outbound.persistence.OrdersPort;
import com.elijahkx.orders.adapters.outbound.persistence.repositories.OrdersRepository;

@Component
public class OrdersAdapter implements OrdersPort {

    private final OrdersMapper ordersMapper;

    private final OrdersRepository ordersRepository;

    public OrdersAdapter(OrdersMapper ordersMapper, OrdersRepository ordersRepository) {
        this.ordersMapper = ordersMapper;
        this.ordersRepository = ordersRepository;
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
        return ordersMapper.entityToDomain(ordersRepository.save(ordersMapper.domainToEntity(order)));
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
