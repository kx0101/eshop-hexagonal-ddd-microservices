package com.elijahkx.customers.service.customers;

import org.springframework.stereotype.Service;

import com.elijahkx.customers.domain.customers.CustomerDomain;
import com.elijahkx.customers.outbound.persistence.CustomersPort;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final CustomersPort customersPort;

    public CustomersServiceImpl(CustomersPort customersPort) {
        this.customersPort = customersPort;
    }

    @Override
    public CustomerDomain addCustomer(CustomerDomain customer) {
        return customersPort.addCustomer(customer);
    }
}
