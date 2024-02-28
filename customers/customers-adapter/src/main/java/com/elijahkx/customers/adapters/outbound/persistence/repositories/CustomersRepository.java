package com.elijahkx.customers.adapters.outbound.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elijahkx.customers.adapters.outbound.persistence.entities.customer.CustomerEntity;

@Repository
public interface CustomersRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);

    <S extends CustomerEntity> S save(S customer);

    @Modifying
    @Query("DELETE FROM CustomerEntity c WHERE c.email = :email")
    void deleteByEmail(@Param("email") String email);

    @Query("SELECT c FROM CustomerEntity c WHERE " +
            "(:name IS NULL OR c.name LIKE %:name%) AND " +
            "(:email IS NULL OR c.email LIKE %:email%)")
    Page<CustomerEntity> findByCriteria(String name, String email, Pageable pageable);
}
