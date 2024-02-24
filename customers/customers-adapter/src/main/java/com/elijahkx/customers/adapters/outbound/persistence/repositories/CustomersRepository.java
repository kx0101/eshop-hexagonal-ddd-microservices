package com.elijahkx.customers.adapters.outbound.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.elijahkx.customers.adapters.outbound.persistence.entities.customer.CustomerEntity;

@Repository
public interface CustomersRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);

    @Modifying
    @Query("DELETE FROM CustomerEntity c WHERE c.email = :email")
    void deleteByEmail(@Param("email") String email);
}
