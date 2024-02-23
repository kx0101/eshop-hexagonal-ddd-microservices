package com.elijahkx.customers.adapters.outbound.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elijahkx.customers.adapters.outbound.persistence.entities.customer.CustomerEntity;

@Repository
public interface CustomersRepository extends JpaRepository<CustomerEntity, Long> {

}
