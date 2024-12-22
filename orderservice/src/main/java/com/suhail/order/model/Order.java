package com.suhail.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.time.LocalDate;


/**
 * Represents an Order entity in the database.
 * This class is mapped to the "orders" table in the database.
 * It contains information about the order such as the customer name, product, quantity, price, and creation date.
 * The class implements Serializable for object serialization.
 *
 * @author Md Suhail Khan
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_Id")

    private Long id;

   @Column(name = "customer_name")
    private String customerName;


   @Column(name = "product")
    private String product;


   @Column(name = "quantity")
    private int quantity;


   @Column(name = "price")
    private double price;


   @Column(name = "create_At")
    private LocalDate createdAt;
}
