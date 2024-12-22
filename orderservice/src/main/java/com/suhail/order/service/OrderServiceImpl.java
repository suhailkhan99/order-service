package com.suhail.order.service;

import com.suhail.order.exception.OrderNotFoundException;
import com.suhail.order.model.Order;
import com.suhail.order.repository.OrderRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementation of the OrderService interface.
 * This class contains the business logic for managing orders, including CRUD operations
 * and asynchronous order processing. Caching annotations are used for optimized performance.
 *
 * @author Md Suhail Khan
 */
@Service
public class OrderServiceImpl implements OrderService{
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retrieves an order by its ID with caching enabled.
     *
     * @param orderId the ID of the order to retrieve
     * @return the order with the specified ID
     * @throws OrderNotFoundException if the order is not found
     */
    @Override
    @Retryable(value = {OrderNotFoundException.class, RuntimeException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 2000))
    @Cacheable(value = "orders", key = "#orderId")
    public Order getOrderById(Long orderId) {
        logger.info("Fetching order with ID: {}", orderId);
        return orderRepository.findById(orderId)
                .map(order -> {
                    logger.info("Order found with ID: {}", orderId);
                    return order;
                })
                .orElseThrow(() -> {
                    logger.error("Order with ID: {} not found", orderId);
                    return new OrderNotFoundException("Order not found with ID: " + orderId);
                });
    }


    /**
     * Saves a new order in the database.
     *
     * @param order the order to save
     * @return the saved order
     */
    @Override
    @Retryable(value = {RuntimeException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public Order saveOrder(Order order) {
        logger.info("saving order with ID: {}", order.getId());
        Order savedOrder = orderRepository.save(order);
        logger.info("Order with ID: {} saved successfully", savedOrder.getId());
        return savedOrder;
    }

    /**
     * Processes a list of orders asynchronously using a fixed thread pool.
     *
     * @param orders the list of orders to process
     */
    @Override
    @Retryable(value = {RuntimeException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void processOrderAsync(List<Order> orders) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(Order order : orders){
            executorService.submit(() ->
            {
                try{
                    logger.info("order with ID: {}", order.getId());
                    saveOrder(order);
                }catch(Exception e) {
                    Thread.currentThread().interrupt();
                }

            });
        }
        executorService.shutdown();
    }

    /**
     * Deletes an order by its ID and evicts it from the cache.
     *
     * @param orderId the ID of the order to delete
     * @throws OrderNotFoundException if the order does not exist
     */
    @Override
    @Retryable(value = {OrderNotFoundException.class, RuntimeException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 2000))
    @CacheEvict(value = "orders", key = "#orderId")
    public void deleteOrder(Long orderId) {
        logger.info("Deleting order with ID: {}", orderId);
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
        logger.info("Order with ID: {} deleted successfully", orderId);
    }

    /**
     * Updates an existing order in the database.
     *
     * @param orderId the ID of the order to update
     * @param order the new order data
     * @return the updated order
     * @throws OrderNotFoundException if the order is not found
     */
    @Override
    @Retryable(value = {RuntimeException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public Order updateOrder(Long orderId, Order order) {
        Optional<Order> existingOrder = orderRepository.findById(order.getId());
        if(existingOrder.isEmpty()){
            logger.error("Order with ID {} not found.", order.getId());
            throw new OrderNotFoundException("Order not Found");
        }
        Order orderToUpdate = existingOrder.get();
        orderToUpdate.setCustomerName(order.getCustomerName());
        orderToUpdate.setProduct(order.getProduct());
        orderToUpdate.setQuantity(order.getQuantity());
        orderToUpdate.setPrice(order.getPrice());
        orderToUpdate.setCreatedAt(order.getCreatedAt());

        Order savedOrder =  orderRepository.save(orderToUpdate);
        logger.info("Order with ID {} successfully updated.", savedOrder.getId());
        return savedOrder;

    }

    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all orders
     */
    @Override
    @Retryable(value = {RuntimeException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public List<Order> getAllOrder() {
        logger.info("Getting All order");

        return orderRepository.findAll();
    }

    /**
     * Deletes all orders from the database.
     */
    @Override
    @Retryable(value = {RuntimeException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
