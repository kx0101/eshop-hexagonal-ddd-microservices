package com.elijahkx.orders.adapters.outbound.persistence.dbadapter;

import feign.FeignException.NotFound;
import java.util.Collections;
import java.util.List;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.elijahkx.customers.adapters.outbound.rest.CustomersClient;
import com.elijahkx.customers.rest.dto.Customer;
import com.elijahkx.orders.adapters.mappers.orders.OrdersMapper;
import com.elijahkx.orders.adapters.outbound.persistence.entities.orders.OrderEntity;
import com.elijahkx.orders.adapters.outbound.persistence.repositories.OrdersRepository;
import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.orders.outbound.kafka.OrderEventPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrdersAdapterTest {

    @Mock
    private OrdersMapper ordersMapper;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private CustomersClient customersClient;

    @Mock
    private OrderEventPort orderEventPort;

    @InjectMocks
    private OrdersAdapter ordersDBAdapter;

    @Test
    void findByCriteria_shouldReturnOrdersDomain() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderEntity> orderEntities = mock(Page.class);

        when(ordersRepository.findByCriteria(1L, pageable)).thenReturn(orderEntities);
        when(ordersMapper.entityToDomain(anyList())).thenReturn(Collections.singletonList(mock(OrderDomain.class)));

        // Act
        List<OrderDomain> result = ordersDBAdapter.findByCriteria(1L, 0, 10);

        // Assert
        assertNotNull(result);
        verify(ordersRepository, times(1)).findByCriteria(1L, pageable);
        verify(ordersMapper, times(1)).entityToDomain(anyList());
    }

    @Test
    void findById_shouldReturnOrderDomain() {
        // Arrange
        when(ordersMapper.entityToDomain(any(OrderEntity.class))).thenReturn(new OrderDomain());
        when(ordersRepository.findById(1L)).thenReturn(Optional.of(new OrderEntity()));

        // Act
        Optional<OrderDomain> result = ordersDBAdapter.findById(1L);

        // Assert
        assertNotNull(result);
        verify(ordersMapper, times(1)).entityToDomain(any(OrderEntity.class));
        verify(ordersRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowNotFound() {
        // Arrange
        when(ordersRepository.findById(1L)).thenThrow(NotFound.class);

        // Act and Assert
        assertThrows(NotFound.class, () -> ordersDBAdapter.findById(1L));
    }

    @Test
    void addOrder_shouldReturnOrderDomain() {
        // Arrange
        OrderDomain orderDomain = new OrderDomain();
        orderDomain.setCustomerId(1L);

        when(customersClient.findById(orderDomain.getCustomerId())).thenReturn(ResponseEntity.ok(mock(Customer.class)));
        when(ordersMapper.domainToEntity(orderDomain)).thenReturn(mock(OrderEntity.class));
        when(ordersRepository.save(any(OrderEntity.class))).thenReturn(mock(OrderEntity.class));
        when(ordersMapper.entityToDomain(any(OrderEntity.class))).thenReturn(mock(OrderDomain.class));

        // Act
        OrderDomain result = ordersDBAdapter.addOrder(orderDomain);

        // Assert
        assertNotNull(result);
        verify(customersClient, times(1)).findById(orderDomain.getCustomerId());
        verify(ordersMapper, times(1)).domainToEntity(orderDomain);
        verify(ordersRepository, times(1)).save(any(OrderEntity.class));
        verify(orderEventPort, times(1)).produce(any(OrderDomain.class));
        verify(ordersMapper, times(1)).entityToDomain(any(OrderEntity.class));
    }

    @Test
    void addOrder_shouldThrowNotFoundForCustomer() {
        // Arrange
        OrderDomain orderDomain = new OrderDomain();
        orderDomain.setCustomerId(1L);

        when(customersClient.findById(orderDomain.getCustomerId())).thenThrow(NotFound.class);

        // Act and Assert
        assertThrows(NotFound.class, () -> ordersDBAdapter.addOrder(orderDomain));
    }

}