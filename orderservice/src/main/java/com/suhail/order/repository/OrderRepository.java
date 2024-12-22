package com.suhail.order.repository;

import com.suhail.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Order entities.
 * This interface provides CRUD operations and query execution for the Order entity.
 * It extends JpaRepository to inherit standard data access operations.
 *
 * @author Md Suhail Khan
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
