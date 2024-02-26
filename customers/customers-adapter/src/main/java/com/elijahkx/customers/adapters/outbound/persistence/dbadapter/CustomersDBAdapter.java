package com.elijahkx.customers.adapters.outbound.persistence.dbadapter;

import com.elijahkx.customers.adapters.outbound.persistence.repositories.CustomersRepository;
import com.elijahkx.customers.domain.customers.CustomerDomain;
import com.elijahkx.customers.outbound.persistence.CustomersPort;
import com.elijahkx.exceptions.EmailAlreadyExistsException;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.elijahkx.customers.adapters.mappers.customers.CustomersMapper;

@Component
public class CustomersDBAdapter implements CustomersPort {

    private final CustomersRepository customersRepository;

    private final CustomersMapper customersMapper;

    public CustomersDBAdapter(CustomersRepository customersRepository, CustomersMapper customersMapper) {
        this.customersRepository = customersRepository;
        this.customersMapper = customersMapper;
    }

    @Override
    public List<CustomerDomain> findByCriteria() {
        return customersMapper.entityToDomain(customersRepository.findAll());
    }

    @Override
    public CustomerDomain addCustomer(CustomerDomain customerDomain) {
        emailExists(customerDomain.getEmail());

        return customersMapper.entityToDomain(customersRepository.save(customersMapper.domainToEntity(customerDomain)));
    }

    @Override
    public Optional<CustomerDomain> findCustomerDomainById(Long id) {
        return customersRepository.findById(id).map(customersMapper::entityToDomain);
    }

    @Override
    public Optional<CustomerDomain> findCustomerDomainByEmail(String email) {
        return customersRepository.findByEmail(email).map(customersMapper::entityToDomain);
    }

    @Override
    public CustomerDomain updateCustomer(CustomerDomain customerDomain) {
        return customersMapper.entityToDomain(customersRepository.save(customersMapper.domainToEntity(customerDomain)));
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        customersRepository.deleteByEmail(email);
    }

    private void emailExists(String email) {
        Optional<CustomerDomain> customer = findCustomerDomainByEmail(email);

        if (customer.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }
}
