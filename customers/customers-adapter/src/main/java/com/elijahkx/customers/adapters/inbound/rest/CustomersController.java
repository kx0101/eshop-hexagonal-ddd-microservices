package com.elijahkx.customers.adapters.inbound.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elijahkx.customers.adapters.mappers.customers.CustomersMapper;
import com.elijahkx.customers.domain.customers.CustomerDomain;
import com.elijahkx.customers.rest.api.CustomersApi;
import com.elijahkx.customers.rest.dto.Customer;
import com.elijahkx.customers.service.customers.CustomersService;

@RestController
public class CustomersController implements CustomersApi {

    private final CustomersService customersService;

    private final CustomersMapper customersMapper;

    public CustomersController(CustomersService customersService, CustomersMapper customersMapper) {
        this.customersService = customersService;
        this.customersMapper = customersMapper;
    }

    @Override
    public ResponseEntity<List<Customer>> findByCriteria(String name, String email) {
        return ResponseEntity.ok(customersMapper.domainToDto(customersService.findByCriteria()));
    }

    @Override
    public ResponseEntity<Customer> addCustomer(Customer customer) {
        return ResponseEntity
                .ok(customersMapper.domainToDto(customersService.addCustomer(customersMapper.dtoToDomain(customer))));
    }

    @Override
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Optional<CustomerDomain> customerDomainOptional = customersService.findById(id);

        return customerDomainOptional
                .map(customerDomain -> ResponseEntity.ok(customersMapper.domainToDto(customerDomain)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
