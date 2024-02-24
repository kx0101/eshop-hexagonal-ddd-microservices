package com.elijahkx.customers.service.customers;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<CustomerDomain> findByCriteria() {
        return customersPort.findByCriteria();
    }

    @Override
    public Optional<CustomerDomain> findById(Long id) {
        return customersPort.findCustomerDomainById(id);
    }
}
