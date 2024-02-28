package com.elijahkx.orders.service.orders;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.orders.outbound.persistence.OrdersPort;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersPort ordersPort;

    public OrdersServiceImpl(OrdersPort ordersPort) {
        this.ordersPort = ordersPort;
    }

    @Override
    public List<OrderDomain> findByCriteria(Long customerId, int page, int size) {
        return ordersPort.findByCriteria(customerId, page, size);
    }

    @Override
    public Optional<OrderDomain> findById(Long id) {
        return ordersPort.findById(id);
    }

    @Override
    public OrderDomain addOrder(OrderDomain order) {
        return ordersPort.addOrder(order);
    }

    @Override
    public OrderDomain updateOrder(OrderDomain order) {
        return ordersPort.updateOrder(order);
    }

    @Override
    public void deleteOrder(Long id) {
        ordersPort.deleteOrder(id);
    }
}
