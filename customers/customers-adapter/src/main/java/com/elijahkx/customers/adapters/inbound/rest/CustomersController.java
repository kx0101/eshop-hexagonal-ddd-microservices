package com.elijahkx.customers.adapters.inbound.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elijahkx.customers.adapters.mappers.customers.CustomersMapper;
import com.elijahkx.customers.domain.customers.CustomerDomain;

import com.elijahkx.utils.ElijahErrorResponse;

import com.elijahkx.customers.rest.api.CustomersApi;
import com.elijahkx.customers.rest.dto.Customer;
import com.elijahkx.customers.service.customers.CustomersService;

import jakarta.validation.Valid;

@RestController
@Validated
public class CustomersController implements CustomersApi {

    private final CustomersService customersService;

    private final CustomersMapper customersMapper;

    public CustomersController(CustomersService customersService, CustomersMapper customersMapper) {
        this.customersService = customersService;
        this.customersMapper = customersMapper;
    }

    @Override
    public ResponseEntity<List<Customer>> findByCriteria(String name, String email, int page, int size) {
        return ResponseEntity.ok(customersMapper.domainToDto(customersService.findByCriteria(name, email, page, size)));
    }

    @Override
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer) {
        return ResponseEntity
                .ok(customersMapper.domainToDto(customersService.addCustomer(customersMapper.dtoToDomain(customer))));
    }

    @Override
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Optional<CustomerDomain> customerDomainOptional = customersService.findCustomerDomainById(id);

        return customerDomainOptional
                .map(customerDomain -> ResponseEntity.ok(customersMapper.domainToDto(customerDomain)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Object> deleteCustomerByEmail(@PathVariable String email) {
        Optional<CustomerDomain> customerDomainOptional = customersService.findCustomerDomainByEmail(email);

        if (customerDomainOptional.isPresent()) {
            customersService.deleteCustomerByEmail(email);

            return ResponseEntity.ok().build();
        }

        ElijahErrorResponse errorResponse = new ElijahErrorResponse(HttpStatus.NOT_FOUND,
                "Customer not found for email: " + email);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @Override
    public ResponseEntity<Object> updateCustomer(@RequestBody @Valid Customer customer, @PathVariable Long id) {
        Optional<CustomerDomain> customerDomainOptional = customersService.findCustomerDomainById(id);

        if (customerDomainOptional.isPresent()) {
            CustomerDomain customerDomain = customerDomainOptional.get();

            customerDomain.setEmail(customer.getEmail());
            customerDomain.setName(customer.getName());

            return ResponseEntity.ok(customersMapper.domainToDto(customersService.updateCustomer(customerDomain)));
        } else {
            ElijahErrorResponse errorResponse = new ElijahErrorResponse(HttpStatus.NOT_FOUND, "Customer not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
