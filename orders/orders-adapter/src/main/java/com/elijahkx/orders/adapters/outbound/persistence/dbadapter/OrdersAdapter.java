package com.elijahkx.orders.adapters.outbound.persistence.dbadapter;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.orders.adapters.mappers.orders.OrdersMapper;
import com.elijahkx.orders.outbound.persistence.OrdersPort;
import com.elijahkx.orders.adapters.outbound.persistence.repositories.OrdersRepository;

@Component
public class OrdersAdapter implements OrdersPort {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersRepository ordersRepository;

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
