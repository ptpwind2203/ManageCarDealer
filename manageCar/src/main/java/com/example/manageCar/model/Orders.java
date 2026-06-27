package com.example.manageCar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Integer orderId;

    @Column(name = "CustomerID")
    private Integer customerId;

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name = "NumberPhone")
    private String numberPhone;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;

    @Column(name = "Email")
    private String email;

    @Column(name = "Address")
    private String address;

    @Column(name = "Status")
    private String status;
}
