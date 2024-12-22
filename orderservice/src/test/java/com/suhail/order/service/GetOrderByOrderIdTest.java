package com.suhail.order.service;

import com.suhail.order.model.Order;
import com.suhail.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class GetOrderByOrderIdTest {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService service;

    private Order order;

    @BeforeEach
    public void setup(){
        Order order = new Order();
        order.setCustomerName("Suhail Khan");
        order.setProduct("Laptop");
        order.setPrice(2190.00);
        order.setCreatedAt(LocalDate.of(2024,12,12));
        repository.save(order);
    }

    @Test
    public void testGetOrderById_CacheHit(){
        Order firstCall = service.getOrderById(order.getId());
        assertNotNull(firstCall);

        // Second call, it will hit the cache
        Order secondCall = service.getOrderById(order.getId());
        assertSame(firstCall, secondCall);
    }
}
