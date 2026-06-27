package com.example.manageCar.repository;

import com.example.manageCar.model.OrderDetail;
import com.example.manageCar.model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByIdOrderId(Integer orderId);
}
