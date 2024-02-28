package com.elijahkx.orders.adapters.outbound.persistence.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.elijahkx.orders.adapters.outbound.persistence.entities.orders.OrderEntity;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE (:customerId IS NULL OR o.customerId = :customerId)")
    Page<OrderEntity> findByCriteria(Long customerId, Pageable pageable);
}
