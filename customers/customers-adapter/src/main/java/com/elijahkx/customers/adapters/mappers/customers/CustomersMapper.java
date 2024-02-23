package com.elijahkx.customers.adapters.mappers.customers;

import java.util.List;

import org.mapstruct.Mapper;

import com.elijahkx.customers.rest.dto.Customer;
import com.elijahkx.customers.adapters.outbound.persistence.entities.customer.CustomerEntity;
import com.elijahkx.customers.domain.customers.CustomerDomain;

@Mapper(componentModel = "spring")
public interface CustomersMapper {

    Customer domainToDto(CustomerDomain customerDomain);

    CustomerDomain dtoToDomain(Customer customer);

    CustomerEntity domainToEntity(CustomerDomain customerDomain);

    CustomerDomain entityToDomain(CustomerEntity customerEntity);

    List<Customer> domainToDto(List<CustomerDomain> customerDomain);

    List<CustomerDomain> dtoToDomain(List<Customer> customer);

    List<CustomerDomain> entityToDomain(List<CustomerEntity> customerEntity);

    List<CustomerEntity> domainToEntity(List<CustomerDomain> customerDomain);
}
