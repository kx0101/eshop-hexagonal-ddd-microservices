package com.elijahkx.customers.service.customers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elijahkx.customers.domain.customers.CustomerDomain;
import com.elijahkx.customers.exceptions.EmailAlreadyExistsException;
import com.elijahkx.customers.outbound.persistence.CustomersPort;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final CustomersPort customersPort;

    public CustomersServiceImpl(CustomersPort customersPort) {
        this.customersPort = customersPort;
    }

    @Override
    public CustomerDomain addCustomer(CustomerDomain customer) {
        emailExists(customer.getEmail());

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

    @Override
    public Optional<CustomerDomain> findByEmail(String email) {
        return customersPort.findByEmail(email);
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

    private void emailExists(String email) {
        Optional<CustomerDomain> customer = customersPort.findByEmail(email);

        if (customer.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }
}
