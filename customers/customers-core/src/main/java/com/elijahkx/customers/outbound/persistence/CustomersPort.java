package com.elijahkx.customers.outbound.persistence;

import java.util.List;
import java.util.Optional;

import com.elijahkx.customers.domain.customers.CustomerDomain;

public interface CustomersPort {
    List<CustomerDomain> findByCriteria();

    CustomerDomain addCustomer(CustomerDomain customer);

    Optional<CustomerDomain> findCustomerDomainById(Long id);
}
