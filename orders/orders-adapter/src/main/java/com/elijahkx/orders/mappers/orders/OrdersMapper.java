package com.elijahkx.orders.mappers.orders;

import java.util.List;

import org.mapstruct.Mapper;

import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.orders.outbound.persistence.entities.orders.OrderEntity;
import com.elijahkx.orders.rest.dto.Order;

@Mapper
public interface OrdersMapper {

    Order domainToDto(OrderDomain orderDomain);

    OrderDomain dtoToDomain(Order order);

    OrderEntity domainToEntity(OrderDomain orderDomain);

    OrderDomain entityToDomain(OrderEntity orderEntity);

    List<Order> domainToDto(List<OrderDomain> orderDomain);

    List<OrderDomain> dtoToDomain(List<Order> order);

    List<OrderEntity> domainToEntity(List<OrderDomain> orderDomain);

    List<OrderDomain> entityToDomain(List<OrderEntity> orderEntity);
}
