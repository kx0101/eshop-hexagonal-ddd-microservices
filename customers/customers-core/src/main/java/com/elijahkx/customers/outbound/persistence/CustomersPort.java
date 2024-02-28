package com.elijahkx.customers.outbound.persistence;

import java.util.List;
import java.util.Optional;

import com.elijahkx.customers.domain.customers.CustomerDomain;

public interface CustomersPort {
    List<CustomerDomain> findByCriteria(String name, String email, int page, int size);

    CustomerDomain addCustomer(CustomerDomain customer);

    CustomerDomain updateCustomer(CustomerDomain customer);

    Optional<CustomerDomain> findCustomerDomainById(Long id);

    Optional<CustomerDomain> findCustomerDomainByEmail(String email);

    void deleteCustomerByEmail(String email);
}
