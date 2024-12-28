package com.suhail.order.util;

import com.suhail.order.exception.ValidationException;
import com.suhail.order.model.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ValidateUtilTest {
    @Test
    void testValidateOrder_NullOrder() {
        assertThrows(ValidationException.class, () -> ValidateUtil.validateOrder(null));
    }

    @Test
    void testValidateOrder_InvalidCustomerName() {
        Order order = new Order(1L, "", "Product", 5, 100.0, LocalDate.now());
        assertThrows(ValidationException.class, () -> ValidateUtil.validateOrder(order));
    }

    @Test
    void testValidateOrders_EmptyList() {
        assertThrows(ValidationException.class, () -> ValidateUtil.validateOrders(Collections.emptyList()));
    }
}