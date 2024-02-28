package com.elijahkx.customers.service.customers;

import java.util.List;
import java.util.Optional;

import com.elijahkx.customers.domain.customers.CustomerDomain;

public interface CustomersService {
    List<CustomerDomain> findByCriteria(String name, String email, int page, int size);

    CustomerDomain addCustomer(CustomerDomain customer);

    CustomerDomain updateCustomer(CustomerDomain customer);

    Optional<CustomerDomain> findCustomerDomainByEmail(String email);

    Optional<CustomerDomain> findCustomerDomainById(Long id);

    void deleteCustomerByEmail(String email);
}
