package com.elijahkx.customers.outbound.persistence;

import com.elijahkx.customers.domain.customers.CustomerDomain;

public interface CustomersPort {
    public CustomerDomain addCustomer(CustomerDomain customer);
}
