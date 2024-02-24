package com.elijahkx.customers.service.customers;

import java.util.List;
import java.util.Optional;

import com.elijahkx.customers.domain.customers.CustomerDomain;

public interface CustomersService {
    List<CustomerDomain> findByCriteria();

    CustomerDomain addCustomer(CustomerDomain customer);

    Optional<CustomerDomain> findById(Long id);
}
