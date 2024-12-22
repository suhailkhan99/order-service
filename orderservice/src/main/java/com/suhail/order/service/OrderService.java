package com.suhail.order.service;


import com.suhail.order.model.Order;

import java.util.List;

/**
 * Service interface for managing orders.
 * Provides methods for CRUD operations and additional order processing tasks.
 * Implementations of this interface should handle business logic related to orders.
 *
 * @author Md Suhail Khan
 */
public interface OrderService {

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to retrieve
     * @return the order with the specified ID
     * @author Md Suhail Khan
     */
    Order getOrderById(Long orderId);

    /**
     * Saves a new order.
     *
     * @param order the order to save
     * @return the saved order
     */
    Order saveOrder(Order order);

    /**
     * Processes a list of orders asynchronously.
     *
     * @param orders the list of orders to process
     */
    void processOrderAsync(List<Order> orders);

    /**
     * Deletes an order by its ID.
     *
     * @param orderId the ID of the order to delete
     */
    void deleteOrder(Long orderId);


    /**
     * Updates an existing order.
     *
     * @param orderId the ID of the order to update
     * @param order the updated order data
     * @return the updated order
     */
    Order updateOrder(Long orderId, Order order);

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    List<Order> getAllOrder();

    /**
     * Deletes all orders from the database.
     */
    void deleteAll();


}
