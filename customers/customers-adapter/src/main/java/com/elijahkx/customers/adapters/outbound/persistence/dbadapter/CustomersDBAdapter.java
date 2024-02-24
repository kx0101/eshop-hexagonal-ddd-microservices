package com.elijahkx.customers.adapters.outbound.persistence.dbadapter;

import com.elijahkx.customers.adapters.outbound.persistence.entities.customer.CustomerEntity;
import com.elijahkx.customers.adapters.outbound.persistence.repositories.CustomersRepository;
import com.elijahkx.customers.domain.customers.CustomerDomain;
import com.elijahkx.customers.outbound.persistence.CustomersPort;

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
    public CustomerDomain addCustomer(CustomerDomain customerDomain) {
        return customersMapper.entityToDomain(customersRepository.save(customersMapper.domainToEntity(customerDomain)));
    }

    @Override
    public List<CustomerDomain> findByCriteria() {
        return customersMapper.entityToDomain(customersRepository.findAll());
    }

    @Override
    public Optional<CustomerDomain> findCustomerDomainById(Long id) {
        Optional<CustomerEntity> customerEntity = customersRepository.findById(id);

        return customerEntity.map(customersMapper::entityToDomain);
    }
}
