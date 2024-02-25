package com.elijahkx.customers.service.customers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elijahkx.customers.domain.customers.CustomerDomain;
import com.elijahkx.customers.outbound.persistence.CustomersPort;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final CustomersPort customersPort;

    public CustomersServiceImpl(CustomersPort customersPort) {
        this.customersPort = customersPort;
    }

    @Override
    public List<CustomerDomain> findByCriteria() {
        return customersPort.findByCriteria();
    }

    @Override
    public CustomerDomain addCustomer(CustomerDomain customer) {
        return customersPort.addCustomer(customer);
    }

    @Override
    public Optional<CustomerDomain> findCustomerDomainById(Long id) {
        return customersPort.findCustomerDomainById(id);
    }

    @Override
    public Optional<CustomerDomain> findCustomerDomainByEmail(String email) {
        return customersPort.findCustomerDomainByEmail(email);
    }

    @Override
    public CustomerDomain updateCustomer(CustomerDomain customer) {
        return customersPort.updateCustomer(customer);
    }

    @Override
    @Transactional
    public void deleteCustomerByEmail(String email) {
        customersPort.deleteCustomerByEmail(email);
    }
}
