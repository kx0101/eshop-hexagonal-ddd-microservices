package com.elijahkx.orders.outbound.kafka;

import com.elijahkx.orders.domain.orders.OrderDomain;

public interface OrderEventPort {
    void produce(OrderDomain orderDomain);
}
