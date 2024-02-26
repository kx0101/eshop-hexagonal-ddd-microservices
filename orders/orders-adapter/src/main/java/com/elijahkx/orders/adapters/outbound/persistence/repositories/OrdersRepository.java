package com.elijahkx.orders.adapters.outbound.persistence.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.elijahkx.orders.adapters.outbound.persistence.entities.orders.OrderEntity;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {

}
