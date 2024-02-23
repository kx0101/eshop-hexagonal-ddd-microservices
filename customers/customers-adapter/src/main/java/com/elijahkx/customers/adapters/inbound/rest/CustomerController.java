package com.elijahkx.customers.adapters.inbound.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.elijahkx.customers.rest.api.CustomersApi;
import com.elijahkx.customers.rest.dto.Customer;

@RestController
public class CustomerController implements CustomersApi {

    @Override
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer(1L, "Liakos", "liakos.koulaxis@yahoo.com"));

        return ResponseEntity.ok(customers);
    }

}
