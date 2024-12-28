package com.suhail.order.controller;


import com.suhail.order.exception.OrderNotFoundException;
import com.suhail.order.exception.ValidationException;
import com.suhail.order.model.Order;
import com.suhail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.suhail.order.util.ValidateUtil;

import java.util.List;

/**
 * REST controller for managing orders.
 * Provides endpoints for CRUD operations, batch processing, and retrieval of orders.
 * Handles requests under the `/api/orders` base URL.
 *
 * @author Md Suhail khan
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return ResponseEntity containing the order and HTTP status, or NOT_FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new order.
     *
     * @param order the order to create
     * @return ResponseEntity containing the created order and HTTP status CREATED
     */
    @PostMapping()
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        try {
            ValidateUtil.validateOrder(order);
        }
        catch (ValidationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Order createdOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Processes a batch of orders asynchronously.
     *
     * @param orders the list of orders to process
     * @return ResponseEntity with a message indicating processing has started
     */
    @PostMapping("/batch")
    public ResponseEntity<String> saveOrdersInParallel(@RequestBody List<Order> orders) {
        ValidateUtil.validateOrders(orders);
        orderService.processOrderAsync(orders);
        return ResponseEntity.ok("Orders are being processed in parallel.");
    }


    /**
     * Updates an existing order.
     *
     * @param id           the ID of the order to update
     * @param updatedOrder the updated order data
     * @return ResponseEntity containing the updated order and HTTP status OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        updatedOrder.setId(id);
        Order savedOrder = orderService.updateOrder(id, updatedOrder);
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     * @return ResponseEntity with HTTP status NO_CONTENT if deleted, or NOT_FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Retrieves all orders.
     *
     * @return ResponseEntity containing the list of all orders and HTTP status OK
     */
    @GetMapping("/getAll")
    public ResponseEntity<String> getAllOrders(){

        orderService.getAllOrder();
        return new ResponseEntity<>("orders", HttpStatus.OK);
    }

    /**
     * Deletes all orders.
     *
     * @return ResponseEntity with HTTP status NO_CONTENT if successful
     */
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteOrder() {
        try {
            orderService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/parallel")
    public ResponseEntity<String> getAllOrders2(){

        orderService.getAllOrder2();
        return new ResponseEntity<>("orders", HttpStatus.OK);
    }
}
