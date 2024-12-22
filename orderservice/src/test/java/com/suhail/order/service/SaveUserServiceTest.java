package com.suhail.order.service;

import com.suhail.order.model.Order;
import com.suhail.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class SaveUserServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void testSaveOrder() {
        Order newOrder = new Order();
        newOrder.setCustomerName("Jane Doe");
        newOrder.setProduct("Phone");
        newOrder.setQuantity(2);
        newOrder.setPrice(800.00);
        newOrder.setCreatedAt(LocalDate.of(2024, 12, 21));

        orderService.saveOrder(newOrder);

        assertNotNull(newOrder.getId());
    }

    @Test
    public void testprocessOrderAsync() {


    }
}
