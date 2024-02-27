package com.elijahkx.customers.adapters.outbound.rest;

import org.springframework.cloud.openfeign.FeignClient;

import com.elijahkx.customers.rest.api.CustomersApi;

@FeignClient(name = "customers", url = "http://localhost:8081")
public interface CustomersClient extends CustomersApi {

}
