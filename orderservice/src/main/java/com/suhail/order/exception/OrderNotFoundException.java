package com.suhail.order.exception;

/**
 * Custom exception thrown when an order is not found.
 * This exception extends RuntimeException, making it an unchecked exception.
 * It is typically used in the service layer to indicate a missing order.
 *
 * @author Md Suhail Khan
 */
public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
