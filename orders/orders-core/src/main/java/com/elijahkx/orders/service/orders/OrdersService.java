package com.elijahkx.orders.service.orders;

import java.util.List;
import java.util.Optional;

import com.elijahkx.orders.domain.orders.OrderDomain;

public interface OrdersService {
    List<OrderDomain> findByCriteria();

    Optional<OrderDomain> findById(Long id);

    OrderDomain addOrder(OrderDomain order);

    OrderDomain updateOrder(OrderDomain order);

    void deleteOrder(Long id);
}
