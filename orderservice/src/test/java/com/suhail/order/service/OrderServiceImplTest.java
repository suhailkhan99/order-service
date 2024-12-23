package com.suhail.order.service;

import com.suhail.order.exception.OrderNotFoundException;
import com.suhail.order.model.Order;
import com.suhail.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order mockOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setCustomerName("John Doe");
        mockOrder.setProduct("Laptop");
        mockOrder.setQuantity(1);
        mockOrder.setPrice(1200.0);
    }

    @Test
    void testGetOrderById_Success() {
        when(orderRepository.findById(mockOrder.getId())).thenReturn(Optional.of(mockOrder));

        Order result = orderService.getOrderById(mockOrder.getId());
        assertNotNull(result);
        assertEquals(mockOrder.getId(), result.getId());
        verify(orderRepository, times(1)).findById(mockOrder.getId());
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(mockOrder.getId())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(mockOrder.getId()));
        verify(orderRepository, times(1)).findById(mockOrder.getId());
    }

    @Test
    void testSaveOrder_Success() {
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        Order result = orderService.saveOrder(mockOrder);
        assertNotNull(result);
        assertEquals(mockOrder.getId(), result.getId());
        verify(orderRepository, times(1)).save(mockOrder);
    }

    @Test
    void testDeleteOrder_Success() {
        when(orderRepository.existsById(mockOrder.getId())).thenReturn(true);
        doNothing().when(orderRepository).deleteById(mockOrder.getId());

        orderService.deleteOrder(mockOrder.getId());
        verify(orderRepository, times(1)).existsById(mockOrder.getId());
        verify(orderRepository, times(1)).deleteById(mockOrder.getId());
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepository.existsById(mockOrder.getId())).thenReturn(false);

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(mockOrder.getId()));
        verify(orderRepository, times(1)).existsById(mockOrder.getId());
    }

    @Test
    void testUpdateOrder_Success() {
        when(orderRepository.findById(mockOrder.getId())).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        Order updatedOrder = new Order();
        updatedOrder.setId(mockOrder.getId());
        updatedOrder.setCustomerName("Jane Doe");
        updatedOrder.setProduct("Smartphone");
        updatedOrder.setQuantity(2);
        updatedOrder.setPrice(800.0);

        Order result = orderService.updateOrder(mockOrder.getId(), updatedOrder);

        assertNotNull(result);
        assertEquals(updatedOrder.getCustomerName(), result.getCustomerName());
        verify(orderRepository, times(1)).findById(mockOrder.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateOrder_NotFound() {
        when(orderRepository.findById(mockOrder.getId())).thenReturn(Optional.empty());

        Order updatedOrder = new Order();
        updatedOrder.setId(mockOrder.getId());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(mockOrder.getId(), updatedOrder));
        verify(orderRepository, times(1)).findById(mockOrder.getId());
    }

    @Test
    void testGetAllOrders_Success() {
        List<Order> orders = new ArrayList<>();
        orders.add(mockOrder);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrder();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAllOrders() {
        doNothing().when(orderRepository).deleteAll();

        orderService.deleteAll();
        verify(orderRepository, times(1)).deleteAll();
    }
}