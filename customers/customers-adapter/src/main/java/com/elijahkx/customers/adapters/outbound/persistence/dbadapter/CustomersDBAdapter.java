package com.elijahkx.customers.adapters.outbound.persistence.dbadapter;

import com.elijahkx.customers.adapters.outbound.persistence.repositories.CustomersRepository;
import com.elijahkx.customers.domain.customers.CustomerDomain;
import com.elijahkx.customers.outbound.persistence.CustomersPort;

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
}
