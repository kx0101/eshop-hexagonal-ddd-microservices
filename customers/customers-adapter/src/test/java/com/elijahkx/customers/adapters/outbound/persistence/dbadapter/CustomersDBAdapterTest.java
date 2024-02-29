package com.elijahkx.customers.adapters.outbound.persistence.dbadapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.elijahkx.customers.adapters.mappers.customers.CustomersMapper;
import com.elijahkx.customers.adapters.outbound.persistence.entities.customer.CustomerEntity;
import com.elijahkx.customers.adapters.outbound.persistence.repositories.CustomersRepository;
import com.elijahkx.customers.domain.customers.CustomerDomain;
import com.elijahkx.exceptions.EmailAlreadyExistsException;

import feign.FeignException.NotFound;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomersDBAdapterTest {

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private CustomersMapper customersMapper;

    @InjectMocks
    private CustomersDBAdapter customersDBAdapter;

    @Test
    void findByCriteria_shouldReturnCustomerDomains() {
        // Arange
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerEntity> customerEntityPage = mock(Page.class);

        when(customersRepository.findByCriteria("liakos", "liakos@gmail.com", pageable)).thenReturn(customerEntityPage);
        when(customersMapper.entityToDomain(anyList()))
                .thenReturn(Collections.singletonList(mock(CustomerDomain.class)));

        // Act
        List<CustomerDomain> result = customersDBAdapter.findByCriteria("liakos", "liakos@gmail.com", 0, 10);

        // Assert
        assertFalse(result.isEmpty());
        verify(customersRepository, times(1)).findByCriteria("liakos", "liakos@gmail.com", PageRequest.of(0, 10));
        verify(customersMapper, times(1)).entityToDomain(anyList());
    }

    @Test
    void addCustomer_shouldAddCustomer() {
        // Arange
        CustomerDomain customerDomain = new CustomerDomain();
        when(customersRepository.save(any(CustomerEntity.class))).thenReturn(new CustomerEntity());
        when(customersMapper.domainToEntity(any(CustomerDomain.class))).thenReturn(new CustomerEntity());
        when(customersMapper.entityToDomain(any(CustomerEntity.class))).thenReturn(customerDomain);

        // Act
        CustomerDomain result = customersDBAdapter.addCustomer(customerDomain);

        // Assert
        assertNotNull(result);
        verify(customersRepository, times(1)).save(any(CustomerEntity.class));
        verify(customersMapper, times(1)).domainToEntity(customerDomain);
        verify(customersMapper, times(1)).entityToDomain(any(CustomerEntity.class));
    }

    @Test
    void addCustomer_shouldThrowExceptionWhenEmailExists() {
        // Arange
        CustomerDomain customerDomain = new CustomerDomain();
        when(customersRepository.findByEmail(any())).thenThrow(EmailAlreadyExistsException.class);

        // Act and Assert
        assertThrows(EmailAlreadyExistsException.class, () -> customersDBAdapter.addCustomer(customerDomain));
    }

    @Test
    void findCustomerDomainById_shouldReturnCustomerDomain() {
        // Arange
        when(customersMapper.entityToDomain(any(CustomerEntity.class))).thenReturn(new CustomerDomain());
        when(customersRepository.findById(1L)).thenReturn(Optional.of(new CustomerEntity()));

        // Act
        Optional<CustomerDomain> result = customersDBAdapter.findCustomerDomainById(1L);

        // Assert
        assertTrue(result.isPresent());
        verify(customersRepository, times(1)).findById(1L);
        verify(customersMapper, times(1)).entityToDomain(any(CustomerEntity.class));
    }

    @Test
    void findCustomerDomainById_shouldThrowNotFound() {
        // Arange
        when(customersRepository.findById(1L)).thenThrow(NotFound.class);

        // Act and Assert
        assertThrows(NotFound.class, () -> customersDBAdapter.findCustomerDomainById(1L));
    }

    @Test
    void findCustomerDomainByEmail_shouldReturnCustomerDomain() {
        // Arange
        when(customersMapper.entityToDomain(any(CustomerEntity.class))).thenReturn(new CustomerDomain());
        when(customersRepository.findByEmail("liakos@gmail.com")).thenReturn(Optional.of(new CustomerEntity()));

        // Act
        Optional<CustomerDomain> result = customersDBAdapter.findCustomerDomainByEmail("liakos@gmail.com");

        // Assert
        assertTrue(result.isPresent());
        verify(customersRepository, times(1)).findByEmail("liakos@gmail.com");
        verify(customersMapper, times(1)).entityToDomain(any(CustomerEntity.class));
    }

    @Test
    void findCustomerDomainByEmail_shouldThrowNotFound() {
        // Arange
        when(customersRepository.findByEmail("liakos@gmail.com")).thenThrow(NotFound.class);

        // Act and Assert
        assertThrows(NotFound.class, () -> customersDBAdapter.findCustomerDomainByEmail("liakos@gmail.com"));
    }

    @Test
    void updateCustomer_shouldReturnCustomerDomain() {
        // Arange
        CustomerDomain customerDomain = new CustomerDomain();
        CustomerEntity customerEntity = new CustomerEntity();

        when(customersMapper.domainToEntity(customerDomain)).thenReturn(customerEntity);
        when(customersMapper.entityToDomain(customerEntity)).thenReturn(customerDomain);
        when(customersRepository.save(customerEntity)).thenReturn(customerEntity);

        // Act
        CustomerDomain result = customersDBAdapter.updateCustomer(customerDomain);

        // Assert
        assertEquals(customerDomain, result);
        verify(customersMapper, times(1)).domainToEntity(customerDomain);
        verify(customersMapper, times(1)).entityToDomain(customerEntity);
        verify(customersRepository, times(1)).save(customerEntity);
    }

    @Test
    void updateCustomer_shouldReturnNotFound() {
        // Arange
        CustomerDomain customerDomain = new CustomerDomain();
        CustomerEntity customerEntity = new CustomerEntity();

        when(customersMapper.domainToEntity(customerDomain)).thenReturn(customerEntity);
        when(customersRepository.save(customerEntity)).thenThrow(NotFound.class);

        // Act and Assert
        assertThrows(NotFound.class, () -> customersDBAdapter.updateCustomer(customerDomain));

        // Assert
        verify(customersMapper, times(1)).domainToEntity(customerDomain);
        verify(customersRepository, times(1)).save(customerEntity);
    }

    @Test
    void deleteCustomer_shouldDeleteCustomer() {
        // Arange
        doNothing().when(customersRepository).deleteByEmail("liakos@gmail.com");

        // Act
        customersDBAdapter.deleteCustomerByEmail("liakos@gmail.com");

        // Assert
        verify(customersRepository, times(1)).deleteByEmail("liakos@gmail.com");
    }

    @Test
    void deleteNonExistentCustomer_shouldThrowNotFound() {
        // Arange
        doThrow(NotFound.class).when(customersRepository).deleteByEmail("liakos@gmail.com");

        // Act
        assertThrows(NotFound.class, () -> customersDBAdapter.deleteCustomerByEmail("liakos@gmail.com"));

        // Assert
        verify(customersRepository, times(1)).deleteByEmail("liakos@gmail.com");
    }
}
