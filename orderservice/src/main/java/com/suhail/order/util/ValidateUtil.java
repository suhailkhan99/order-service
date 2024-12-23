package com.suhail.order.util;

import com.suhail.order.exception.ValidationException;
import com.suhail.order.model.Order;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

public class ValidateUtil {
    /**
     * Validates a single Order object.
     *
     * @param order the Order to validate
     * @throws ValidationException if validation fails
     */
    public static void validateOrder(Order order) {
        if (Objects.isNull(order)) {
            throw new ValidationException("Order cannot be null.");
        }
        if (!StringUtils.hasText(order.getCustomerName())) {
            throw new ValidationException("Customer name is required.");
        }
        if (order.getCustomerName().length() < 3 || order.getCustomerName().length() > 20) {
            throw new ValidationException("Customer name must be between 3 and 20 characters.");
        }
        if (!StringUtils.hasText(order.getProduct())) {
            throw new ValidationException("Product is required.");
        }
        if (order.getProduct().length() < 3 || order.getProduct().length() > 20) {
            throw new ValidationException("Product name must be between 3 and 20 characters.");
        }
        if (order.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be greater than 0.");
        }
        if (order.getPrice() <= 0) {
            throw new ValidationException("Price must be greater than 0.");
        }
        if (order.getCreatedAt() == null) {
            throw new ValidationException("Creation date is required.");
        }
    }

    /**
     * Validates a list of Order objects.
     *
     * @param orders the list of orders to validate
     * @throws ValidationException if any validation fails
     */
    public static void validateOrders(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            throw new ValidationException("Order list cannot be null or empty.");
        }
        for (Order order : orders) {
            validateOrder(order);
        }
    }
}
