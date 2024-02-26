package com.elijahkx.orders.outbound.persistence;

import java.util.List;
import java.util.Optional;

import com.elijahkx.orders.domain.orders.OrderDomain;

public interface OrdersPort {
    List<OrderDomain> findByCriteria();

    Optional<OrderDomain> findById(Long id);

    OrderDomain addOrder(OrderDomain order);

    OrderDomain updateOrder(OrderDomain order);

    void deleteOrder(Long id);
}
